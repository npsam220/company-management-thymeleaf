package com.company.backend.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.IntStream;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.company.backend.config.UploadProperties;
import com.company.backend.dto.StaffSearchDto;
import com.company.backend.entity.Staff;
import com.company.backend.service.StaffService;

@Controller
@RequestMapping("/staff")
public class StaffViewController {

    private static final int DEFAULT_PAGE_SIZE = 5;
    private static final List<String> PHOTO_EXTENSIONS = List.of(".jpg", ".jpeg", ".png", ".gif");
    private static final List<String> RESUME_EXTENSIONS = List.of(".pdf", ".doc", ".docx");

    private final StaffService staffService;
    private final UploadProperties uploadProperties;

    public StaffViewController(StaffService staffService, UploadProperties uploadProperties) {
        this.staffService = staffService;
        this.uploadProperties = uploadProperties;
    }

    @GetMapping({ "", "/", "/list" })
    public String list(
            @ModelAttribute("search") StaffSearchDto search,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int pageSize,
            Model model) {

        int safePageSize = List.of(10, 30, 50).contains(pageSize) ? pageSize : DEFAULT_PAGE_SIZE;
        search.setPage(Math.max(page, 1));
        search.setPageSize(safePageSize);

        int totalCount = staffService.countStaff(search);
        int totalPages = Math.max(1, (int) Math.ceil((double) totalCount / safePageSize));
        int currentPage = Math.min(search.getPage(), totalPages);
        search.setPage(currentPage);

        model.addAttribute("staffList", staffService.searchStaff(search));
        model.addAttribute("staffClsList", staffService.findStaffClsList());
        model.addAttribute("salesStatusList", staffService.findSalesStatusList());
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("pageSize", safePageSize);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("pageNumbers",
                IntStream.rangeClosed(1, totalPages).boxed().toList());

        return "staff/list";
    }

    @GetMapping("/detail/{staffId}")
    public String detail(@PathVariable Long staffId, Model model) {
        Staff staff = staffService.getStaffDetail(staffId);
        if (staff == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        addFormAttributes(model, staff, "スタッフ情報更新", "/staff/update");
        return "staff/detail";
    }

    @PostMapping("/update")
    public String update(
            @ModelAttribute Staff staff,
            @RequestParam(required = false) MultipartFile photoFile,
            @RequestParam(required = false) MultipartFile resumeFile,
            Model model,
            RedirectAttributes redirectAttributes) throws IOException {

        Staff current = staffService.getStaffDetail(staff.getStaffId());
        if (current == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        staff.setStaffPhoto(current.getStaffPhoto());
        staff.setStaffResume(current.getStaffResume());

        String validationMessage = validate(staff);
        if (validationMessage != null) {
            model.addAttribute("errorMessage", validationMessage);
            addFormAttributes(model, staff, "スタッフ情報更新", "/staff/update");
            return "staff/detail";
        }

        saveUploads(staff, photoFile, resumeFile);
        staffService.update(staff);
        // 更新後のリダイレクト先を詳細ページに変更
        redirectAttributes.addFlashAttribute("message", "スタッフ情報を更新しました。");
        return "redirect:/staff/detail/" + staff.getStaffId();
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        addFormAttributes(model, new Staff(), "スタッフ新規登録", "/staff/create");
        return "staff/create";
    }

    @PostMapping("/create")
    public String create(
            @ModelAttribute Staff staff,
            @RequestParam(required = false) MultipartFile photoFile,
            @RequestParam(required = false) MultipartFile resumeFile,
            Model model,
            RedirectAttributes redirectAttributes) throws IOException {

        String validationMessage = validate(staff);
        if (validationMessage != null) {
            model.addAttribute("errorMessage", validationMessage);
            addFormAttributes(model, staff, "スタッフ新規登録", "/staff/create");
            return "staff/create";
        }

        staff.setStaffNo(staffService.generateStaffNo(staff.getStaffIndt().toString()));
        saveUploads(staff, photoFile, resumeFile);
        staffService.create(staff);

        redirectAttributes.addFlashAttribute("message", "スタッフを登録しました。");
        return "redirect:/staff/list";
    }

    @PostMapping("/delete/{staffId}")
    public String delete(@PathVariable Long staffId, RedirectAttributes redirectAttributes) {
        staffService.delete(staffId);
        redirectAttributes.addFlashAttribute("message", "スタッフを削除しました。");
        return "redirect:/staff/list";
    }

    @GetMapping("/generate-number")
    @ResponseBody
    public String generateStaffNo(@RequestParam String staffIndt) {
        return staffService.generateStaffNo(staffIndt);
    }

    private void addFormAttributes(Model model, Staff staff, String pageTitle, String formAction) {
        model.addAttribute("staff", staff);
        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("formAction", formAction);
        model.addAttribute("staffClsList", staffService.findStaffClsList());
        model.addAttribute("salesStatusList", staffService.findSalesStatusList());
    }

    private String validate(Staff staff) {
        if (!StringUtils.hasText(staff.getStaffNm())) {
            return "氏名を入力してください。";
        }
        if (!StringUtils.hasText(staff.getStaffSalesstatus())) {
            return "営業ステータスを選択してください。";
        }
        if (!StringUtils.hasText(staff.getStaffSex())) {
            return "性別を選択してください。";
        }
        if (staff.getStaffBirthdt() == null) {
            return "生年月日を入力してください。";
        }
        if (!StringUtils.hasText(staff.getStaffCountry())) {
            return "国籍を選択してください。";
        }
        if (!StringUtils.hasText(staff.getStaffCls())) {
            return "スタッフ区分を選択してください。";
        }
        if (staff.getStaffIndt() == null) {
            return "契約日を入力してください。";
        }
        return null;
    }

    private void saveUploads(Staff staff, MultipartFile photoFile, MultipartFile resumeFile)
            throws IOException {
        if (photoFile != null && !photoFile.isEmpty()) {
            String extension = extensionOf(photoFile.getOriginalFilename(), PHOTO_EXTENSIONS, "写真");
            Path directory = Path.of(uploadProperties.getPhotoDir());
            Files.createDirectories(directory);
            String filename = "staff_" + staff.getStaffNo() + extension;
            Files.copy(photoFile.getInputStream(), directory.resolve(filename),
                    StandardCopyOption.REPLACE_EXISTING);
            staff.setStaffPhoto("/staff_photo/" + filename);
        }

        if (resumeFile != null && !resumeFile.isEmpty()) {
            String extension = extensionOf(resumeFile.getOriginalFilename(), RESUME_EXTENSIONS, "履歴書");
            Path directory = Path.of(uploadProperties.getFileDir());
            Files.createDirectories(directory);
            deleteOldResumeFiles(directory, staff.getStaffNo());
            String filename = "staff_" + staff.getStaffNo() + "_resume" + extension;
            Files.copy(resumeFile.getInputStream(), directory.resolve(filename),
                    StandardCopyOption.REPLACE_EXISTING);
            staff.setStaffResume("/staff_file/" + filename);
        }
    }

    private String extensionOf(String filename, List<String> allowedExtensions, String label) {
        String extension = StringUtils.getFilenameExtension(filename);
        String normalized = extension == null ? "" : "." + extension.toLowerCase();
        if (!allowedExtensions.contains(normalized)) {
            throw new IllegalArgumentException(label + "のファイル形式が正しくありません。");
        }
        return normalized;
    }

    private void deleteOldResumeFiles(Path directory, String staffNo) throws IOException {
        for (String extension : RESUME_EXTENSIONS) {
            Files.deleteIfExists(directory.resolve("staff_" + staffNo + "_resume" + extension));
        }
    }
}

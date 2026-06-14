package com.company.backend.controller;

import com.company.backend.dto.CommonDto;
import com.company.backend.dto.StaffDetailDto;
import com.company.backend.dto.StaffSearchDto;
import com.company.backend.dto.StaffSearchResponseDto;
import com.company.backend.entity.Staff;
import com.company.backend.service.StaffService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.company.backend.config.UploadProperties;

@RestController
@RequestMapping("/api/staff")
public class StaffController {

    private final StaffService staffService;
    private final UploadProperties uploadProperties;

    public StaffController(StaffService staffService, UploadProperties uploadProperties) {
        this.staffService = staffService;
        this.uploadProperties = uploadProperties;
    }

    @GetMapping("/list")
    public List<Staff> findAll() {
        return staffService.findAll();
    }

    @GetMapping("/staffClslist")
    public List<CommonDto> findStaffClsList() {
        return staffService.findStaffClsList();
    }

    @GetMapping("/salesStatusList")
    public List<CommonDto> findSalesStatusList() {
        return staffService.findSalesStatusList();
    }

    @GetMapping("/detail/{staffId}")
    public Staff getStaffDetail(
            @PathVariable Long staffId) {

        return staffService
                .getStaffDetail(
                        staffId);
    }

    @PostMapping("/create")
    public String create(@RequestBody Staff staff) {
        staffService.create(staff);
        return "Staff created successfully";
    }

    @PutMapping("/update")
    public String update(@RequestBody Staff staff) {
        staffService.update(staff);
        return "Staff updated successfully";
    }

    @PostMapping("/photo/upload/{staffNo}")
    public String uploadPhoto(
            @PathVariable String staffNo,
            @RequestParam("file") MultipartFile file) throws IOException {

        if (file.isEmpty()) {
            throw new IllegalArgumentException("写真ファイルを選択してください。");
        }

        String originalFilename = file.getOriginalFilename();
        String extension = "";

        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        }

        if (!List.of(".jpg", ".jpeg", ".png", ".gif").contains(extension)) {
            throw new IllegalArgumentException("写真は jpg, jpeg, png, gif を選択してください。");
        }

        Path uploadDir = Path.of(uploadProperties.getPhotoDir());
        System.out.println("PHOTO UPLOAD DIR = "
                + uploadDir.toAbsolutePath());
        Files.createDirectories(uploadDir);

        String filename = "staff_" + staffNo + extension;
        Path target = uploadDir.resolve(filename);
        Thumbnails.of(file.getInputStream())
                .size(128, 128)
                .outputFormat(extension.substring(1))
                .toFile(target.toFile());

        return "/staff_photo/" + filename;
    }

    @PostMapping("/resume/upload/{staffNo}")
    public String uploadResume(
            @PathVariable String staffNo,
            @RequestParam("file") MultipartFile file) throws IOException {

        if (file.isEmpty()) {
            throw new IllegalArgumentException("履歴書ファイルを選択してください。");
        }

        String originalFilename = file.getOriginalFilename();
        String extension = "";

        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        }

        if (!List.of(".pdf", ".doc", ".docx").contains(extension)) {
            throw new IllegalArgumentException("履歴書は pdf, doc, docx を選択してください。");
        }

        Path uploadDir = Path.of(uploadProperties.getFileDir());
        System.out.println("RESUME UPLOAD DIR = "
                + uploadDir.toAbsolutePath());

        Files.createDirectories(uploadDir);

        String filename = "staff_" + staffNo + "_resume" + extension;
        Path target = uploadDir.resolve(filename);

        deleteOldResumeFiles(uploadDir, staffNo);
        Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

        return "/staff_file/" + filename;
    }

    private void deleteOldResumeFiles(Path dir, String staffNo) throws IOException {
        Files.deleteIfExists(dir.resolve("staff_" + staffNo + "_resume.pdf"));
        Files.deleteIfExists(dir.resolve("staff_" + staffNo + "_resume.doc"));
        Files.deleteIfExists(dir.resolve("staff_" + staffNo + "_resume.docx"));
    }

    @DeleteMapping("/delete/{staffId}")
    public String delete(@PathVariable Long staffId) {
        staffService.delete(staffId);
        return "Staff deleted successfully";
    }

    @PostMapping("/search")
    public List<Staff> searchStaff(
            @RequestBody StaffSearchDto dto) {

        return staffService.searchStaff(dto);

    }

    @PostMapping("/searchPage")
    public StaffSearchResponseDto search(
            @RequestBody StaffSearchDto dto) {

        List<Staff> list = staffService.searchStaff(dto);

        int totalCount = staffService.countStaff(dto);

        StaffSearchResponseDto response = new StaffSearchResponseDto();

        response.setList(list);

        response.setTotalCount(totalCount);

        return response;
    }

    @GetMapping("/generateStaffNo")
    public String generateStaffNo(@RequestParam String staffIndt) {
        return staffService.generateStaffNo(staffIndt);
    }
}

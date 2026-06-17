package com.company.backend.controller;

import java.util.List;
import java.util.stream.IntStream;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.company.backend.dto.CustSearchDto;
import com.company.backend.entity.Bank;
import com.company.backend.entity.Cust;
import com.company.backend.service.BankService;
import com.company.backend.service.CustService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/cust")
public class CustViewController {
    private final CustService custService;
    private final BankService bankService;
    private static final int DEFAULT_PAGE_SIZE = 10;

    public CustViewController(CustService custService, BankService bankService) {
        this.custService = custService;
        this.bankService = bankService;
    }

    @GetMapping({ "", "/", "/list" })
    public String list(
            @ModelAttribute("search") CustSearchDto search,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            Model model) {

        int safePageSize = List.of(10, 30, 50).contains(pageSize) ? pageSize : DEFAULT_PAGE_SIZE;
        search.setPage(Math.max(page, 1));
        search.setPageSize(safePageSize);

        int totalCount = custService.countCust(search);
        int totalPages = Math.max(1, (int) Math.ceil((double) totalCount / safePageSize));
        int currentPage = Math.min(search.getPage(), totalPages);
        search.setPage(currentPage);

        model.addAttribute("custList", custService.searchCust(search));
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("pageSize", safePageSize);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("pageNumbers",
                IntStream.rangeClosed(1, totalPages).boxed().toList());

        return "cust/list";
    }

    @GetMapping("/detail/{custId}")
    public String detail(@PathVariable Long custId, Model model) {
        Cust cust = custService.getCustDetail(custId);
        if (cust == null) {
            return "redirect:/cust/list";
        }
        model.addAttribute("cust", cust);
        model.addAttribute("bankMasters", bankService.findAll());
        return "cust/detail";
    }

    @PostMapping("/update")
    public String update(
            @Valid @ModelAttribute Cust cust,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (cust.getCustId() == null) {
            bindingResult.rejectValue("custId", "custId.required", "取引先IDは必須です。");
        }
        if (bindingResult.hasErrors()) {

            model.addAttribute("bankMasters",
                    bankService.findAll());

            return "cust/detail";
        }

        validateBankMaster(cust, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("bankMasters", bankService.findAll());
            return "cust/detail";
        }

        try {
            custService.update(cust);
        } catch (IllegalArgumentException e) {
            bindingResult.reject("cust.update.failed", e.getMessage());
            model.addAttribute("bankMasters", bankService.findAll());
            return "cust/detail";
        }

        redirectAttributes.addFlashAttribute(
                "message",
                "顧客情報を更新しました。");

        return "redirect:/cust/detail/" + cust.getCustId();
    }

    private void validateBankMaster(Cust cust, BindingResult bindingResult) {
        if (!"01".equals(cust.getCustType())) {
            return;
        }
        if (cust.getBankCd() == null || cust.getBankCd().isBlank()
                || cust.getBankChrcd() == null || cust.getBankChrcd().isBlank()) {
            bindingResult.rejectValue("bankCd", "bank.required", "銀行マスタから銀行情報を選択してください。");
            return;
        }
        Bank bank = bankService.findByCode(cust.getBankCd(), cust.getBankChrcd());
        if (bank == null) {
            bindingResult.rejectValue("bankCd", "bank.notFound", "銀行マスタに存在しない銀行情報です。");
        }
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        addFormAttributes(model, new Cust(), "取引先新規登録", "/cust/create");
        return "cust/create";
    }

    private void addFormAttributes(Model model, Cust cust, String pageTitle, String formAction) {
        model.addAttribute("cust", cust);
        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("formAction", formAction);
        model.addAttribute("bankMasters", bankService.findAll());
    }

    @PostMapping("/create")
    public String create(
            @Valid @ModelAttribute Cust cust,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            addFormAttributes(model, cust, "取引先新規登録", "/cust/create");
            return "cust/create";
        }
        validateBankMaster(cust, bindingResult);
        if (bindingResult.hasErrors()) {
            addFormAttributes(model, cust, "取引先新規登録", "/cust/create");
            return "cust/create";
        }

        try {
            custService.create(cust);
        } catch (IllegalArgumentException e) {
            bindingResult.reject("cust.create.failed", e.getMessage());
            addFormAttributes(model, cust, "取引先新規登録", "/cust/create");
            return "cust/create";
        }

        redirectAttributes.addFlashAttribute("message", "取引先を登録しました。");
        return "redirect:/cust/list";
    }

    @PostMapping("/delete/{custId}")
    public String delete(
            @PathVariable Long custId,
            RedirectAttributes redirectAttributes) {

        custService.deleteCust(custId);

        redirectAttributes.addFlashAttribute(
                "message",
                "取引先を削除しました。");

        return "redirect:/cust/list";
    }
}

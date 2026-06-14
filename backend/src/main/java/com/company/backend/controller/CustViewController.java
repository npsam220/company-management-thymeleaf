package com.company.backend.controller;

import java.util.List;
import java.util.stream.IntStream;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.company.backend.dto.CustSearchDto;
import com.company.backend.service.CustService;

@Controller
@RequestMapping("/cust")
public class CustViewController {
    private final CustService custService;
    private static final int DEFAULT_PAGE_SIZE = 10;

    public CustViewController(CustService custService) {
        this.custService = custService;
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
}

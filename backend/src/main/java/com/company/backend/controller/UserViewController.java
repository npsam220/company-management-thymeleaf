package com.company.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.company.backend.dto.UserSearchDto;
import com.company.backend.service.UserService;

@Controller
@RequestMapping("/user")
public class UserViewController {
    private final UserService userService;

    public UserViewController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping({ "", "/", "/list" })
    public String list(@ModelAttribute("search") UserSearchDto search, Model model) {
        // ユーザー一覧のロジックを実装
        model.addAttribute("userList", userService.searchUsers(search));
        return "user/list";
    }
}

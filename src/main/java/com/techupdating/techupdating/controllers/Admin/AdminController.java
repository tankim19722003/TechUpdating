package com.techupdating.techupdating.controllers.Admin;

import com.techupdating.techupdating.Services.UserService;
import com.techupdating.techupdating.dtos.AdminLoginDTO;
import com.techupdating.techupdating.dtos.UserLoginDTO;
import com.techupdating.techupdating.models.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/dev_updating/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        AdminLoginDTO adminLoginDTO = new AdminLoginDTO();
        model.addAttribute("adminLoginDTO", adminLoginDTO);

        return "/Admin/login-form";
    }

    @PostMapping("/processAdminForm")
    public String processAdminForm(
            @Valid @ModelAttribute("adminLoginDTO") AdminLoginDTO adminLoginDTO,
            BindingResult bindingResult,
            Model model
    ) {

        // check valid input
        if (bindingResult.hasErrors()) return "/Admin/login-form";

        // check account with db
        try {
            User user = userService.loginAdmin(adminLoginDTO);
            model.addAttribute("admin", user);
            return "/Admin/AdminLoginSuccessfully";
        } catch(Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "/Admin/login-form";
        }

    }
}

package com.techupdating.techupdating.controllers;

import com.techupdating.techupdating.dtos.UserLoginDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/v1/dev_updating")
public class UserController {

    @GetMapping("")
    public String showLoginForm(Model model) {
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        model.addAttribute("userLoginDTO", userLoginDTO);
        return "login-form";
    }

    @PostMapping("/processLoginForm")
    public String processLoginForm(
            @ModelAttribute("userLoginDTO") UserLoginDTO userLoginDTO,
            Model model
    ) {
        System.out.println(userLoginDTO);
        model.addAttribute("User", userLoginDTO.toString());
        return "login-successfully";
    }
}

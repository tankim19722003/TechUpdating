package com.techupdating.techupdating.controllers.User;

import com.techupdating.techupdating.Services.UserService;
import com.techupdating.techupdating.dtos.UserLoginDTO;
import com.techupdating.techupdating.dtos.UserRegisterDTO;
import com.techupdating.techupdating.models.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("api/v1/dev_updating")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // check the empty string
    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        model.addAttribute("userLoginDTO", userLoginDTO);

        return "/User/login-form";
    }

    @PostMapping("/processLoginForm")
    public String processLoginForm(
            @Valid @ModelAttribute("userLoginDTO") UserLoginDTO userLoginDTO,
            BindingResult result,
            Model model
    ) {

        if (result.hasErrors()) {
            return "login-form";
        } else {

            try {
                User user = userService.login(userLoginDTO);

                // REDIRECT TO HOME PAGE
                return "/User/login-successfully";
            } catch (Exception e) {
                model.addAttribute("errorMessage", e.getMessage());
                return "/User/login-form";
            }
        }

    }

    @GetMapping("/register")
    public String showRegisterForm(
            Model model
    ) {
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        model.addAttribute("userRegisterDTO", userRegisterDTO);

        return "/User/register-form";

    }

    @PostMapping("/processRegisterForm")
    public String procsessRegisterForm(
        @Valid @ModelAttribute("userRegisterDTO") UserRegisterDTO userRegisterDTO,
        BindingResult theResult,
        Model model
    ) {
        if (theResult.hasErrors()) {
            return "register-form";
        } else {

            // save user
            try {
                User user = userService.register(userRegisterDTO);
            } catch(Exception e) {
                model.addAttribute("errorMessage", e.getMessage());
                return "/User/register-form";
            }


            // redirect to login page
            return "/User/Register-successfully";
        }
    }
}

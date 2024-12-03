package com.techupdating.techupdating.controllers.Admin;

import com.techupdating.techupdating.Services.UserServiceImpl;
import com.techupdating.techupdating.dtos.AdminLoginDTO;
import com.techupdating.techupdating.models.User;
import com.techupdating.techupdating.responses.ErrorResponse;
import com.techupdating.techupdating.responses.UserInfoResponse;
import com.techupdating.techupdating.responses.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/v1/dev_updating/admin")
@RequiredArgsConstructor
public class AdminController {
    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private final UserServiceImpl userService;

    @GetMapping("/show_login_form")
    public String showLoginForm(Model model) {
        AdminLoginDTO adminLoginDTO = new AdminLoginDTO();
        model.addAttribute("adminLoginDTO", adminLoginDTO);

        return "/Admin/login-form";
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Valid @RequestBody AdminLoginDTO adminLoginDTO,
            BindingResult result
    ) {
        // check valid input
        if (result.hasErrors()) {
            List<ErrorResponse> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(fieldError -> ErrorResponse.builder()
                            .name(fieldError.getField())  // Set the field name
                            .error(fieldError.getDefaultMessage())  // Set the error message if needed
                            .build())
                    .collect(Collectors.toList());

            System.out.println(errorMessages);
            return ResponseEntity.badRequest().body(errorMessages);
        }

        // check account with db
        try {
            UserInfoResponse admin = User.toUserInfoResponse(userService.loginAdmin(adminLoginDTO));
            return ResponseEntity.ok().body(admin);
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(
                    ErrorResponse.builder()
                            .name(ErrorResponse.ERROR)
                            .error(e.getMessage())
                            .build()
            );
        }

    }

    @GetMapping("/homepage")
    public String String() {
        return "/Admin/homepage";
    }

}

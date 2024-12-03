package com.techupdating.techupdating.controllers.User;

import com.techupdating.techupdating.Services.UserServiceImpl;
import com.techupdating.techupdating.dtos.UserChangingPassworDTO;
import com.techupdating.techupdating.dtos.UserLoginDTO;
import com.techupdating.techupdating.dtos.UserRegisterDTO;
import com.techupdating.techupdating.dtos.UserTwoWaySecurityDTO;
import com.techupdating.techupdating.models.User;
import com.techupdating.techupdating.responses.ErrorResponse;
import com.techupdating.techupdating.responses.UserInfoResponse;
import com.techupdating.techupdating.responses.UserResponse;
import com.techupdating.techupdating.responses.UserTwoWayResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("${api.prefix}")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

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
    public ResponseEntity<?> processLoginForm(
            @Valid @RequestBody UserLoginDTO userLoginDTO,
            BindingResult result
    ) {
        try {
            if (result.hasErrors()) {
                List<ErrorResponse> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(fieldError -> ErrorResponse.builder()
                                .name(fieldError.getField()) // Get the field name
                                .error(fieldError.getDefaultMessage()) // Get the error message
                                .build()
                        )
                        .collect(Collectors.toList());

                return ResponseEntity.badRequest().body(errorMessages);
            }

            return ResponseEntity.ok().body(User.toUserLoginResponse(userService.login(userLoginDTO)));
        } catch(Exception e) {
            List<ErrorResponse> error =  new ArrayList<ErrorResponse>();
            error.add(new ErrorResponse("error",e.getMessage()));
            return ResponseEntity.badRequest().body(error);

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
    public ResponseEntity procsessRegisterForm(
        @Valid @RequestBody UserRegisterDTO userRegisterDTO,
        BindingResult result,
        Model model
    ) {
        try {
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

            return ResponseEntity.ok().body(userService.register(userRegisterDTO));
        } catch(Exception e) {
            ArrayList <ErrorResponse> errorResponse = new ArrayList<>();
            errorResponse.add(ErrorResponse.builder()
                    .name(ErrorResponse.ERROR)
                    .error(e.getMessage())
                    .build());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @GetMapping("/show_home_page")
    public String showHomePage(
            Model model
    ) {
        // ADD USER TO MODEL

        // redirect to home page
        return "/User/home-page";
    }

    @GetMapping("/show-register-successfully")
    public String showRegisterSuccessfully() {
        return "/User/Register-successfully";
    }

    @GetMapping("/show-user-info-page/{userId}")
    public String showUserInfoPage(
            @PathVariable("userId") int userId,
            Model model
    ) {

        try {
            UserInfoResponse userInfoResponse = userService.findInfoUser(userId);

            // set user infor to model
            model.addAttribute("userInforResponse", userInfoResponse);

            return "/User/user-infor-page";

        }catch (Exception e) {
            // return to show page error
            model.addAttribute("message", e.getMessage());
            return "/User/inform";
        }

    }

    @PutMapping("/update_full_name")
    public ResponseEntity<?> updateFullname(
            @RequestParam("user_id") int userId,
            @RequestParam("fullname") String fullname) {

        try {
            return ResponseEntity.ok().body(userService.updateFullname(userId, fullname));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ArrayList<>().add(new ErrorResponse(
                    "message", e.getMessage()
            )));
        }
    }

    @PutMapping("/update_email")
    public ResponseEntity<?> updateEmail(
            @RequestParam("user_id") int userId,
            @RequestParam("email") String email)
    {

        try {
            return ResponseEntity.ok().body(userService.updateEmail(userId, email));
        } catch (Exception e) {

            ArrayList<ErrorResponse> errorResponses = new ArrayList<>();
            errorResponses.add(new ErrorResponse("error", e.getMessage()));
            return ResponseEntity.badRequest().body(errorResponses);
        }
    }

    @GetMapping("/show-security-password-page/{user_id}")
    public String showUserSecurityAndPasswordPage(
            @PathVariable("user_id") int userId,
            Model model
    ) {
        UserInfoResponse userResponse = userService.findInfoUser(userId);
        if (userResponse != null) {

            // set userResponse to model
            model.addAttribute("userInforResponse", userResponse);

            // return security and password page
            return "/User/user-security-page";
        } else {
            return "/User/login-form";
        }
    }

    @PutMapping("/update-two-way-security")
    public ResponseEntity<?> updateTwoWaySecurity(
            @RequestBody UserTwoWaySecurityDTO userTwoWaySecurityDTO
    ) {

        try {
            UserTwoWayResponse userTwoWayResponse =  userService.updateTwoWaysSecurity(userTwoWaySecurityDTO);
            return ResponseEntity.ok().body(userTwoWayResponse);
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(  ErrorResponse.builder()
                    .name(ErrorResponse.ERROR)
                    .error(e.getMessage())
                    .build());
        }

    }

    @PutMapping("/change-password/{user_id}")
    public ResponseEntity<?> changePassword(
            @PathVariable("user_id") int userId,
            @RequestBody UserChangingPassworDTO userChangingPassworDTO,
            BindingResult result
    ) {
        try {
            if (result.hasErrors()) {
                List<ErrorResponse> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(fieldError -> ErrorResponse.builder()
                                .name(fieldError.getField()) // Get the field name
                                .error(fieldError.getDefaultMessage()) // Get the error message
                                .build()
                        )
                        .collect(Collectors.toList());

                return ResponseEntity.badRequest().body(errorMessages);
            }

            // update password
            userService.changePassword(userId, userChangingPassworDTO);
            return ResponseEntity.ok().body("");

        } catch(Exception e) {

            List<ErrorResponse> errorResponses = new ArrayList<>();
            errorResponses.add(ErrorResponse
                    .builder()
                    .error(e.getMessage())
                    .name(ErrorResponse.ERROR)
                    .build());
            return ResponseEntity
                    .badRequest()
                    .body(errorResponses);
        }

    }
}

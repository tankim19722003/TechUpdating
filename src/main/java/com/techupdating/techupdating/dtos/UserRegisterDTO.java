package com.techupdating.techupdating.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.techupdating.techupdating.validation.Gmail;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRegisterDTO {

    @NotEmpty(message = "Fullname can't be empty")
    private String fullname;

    @NotEmpty(message = "Account is required")
    @Size(min=5, message = "Account must be greater than 5 characters")
    private String account;

    @Size(min=8, message = "Password must be longer greater than 8 characters")
    @NotEmpty(message = "password is required")
    private String password;

    @Size(min=8, message = "Password must be longer greater than 8 characters")
    @NotEmpty(message = "retype password is required")
    @JsonProperty("retype_password")
    private String retypePassword;

    @NotEmpty(message = "email can't be empty")
    @Gmail
    private String email;

}

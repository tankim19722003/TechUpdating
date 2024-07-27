package com.techupdating.techupdating.dtos;

import com.techupdating.techupdating.validation.Gmail;
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

    @NotNull(message = "fullname can't be empty")
    private String fullname;

    @NotNull(message = "account is required")
    @Size(min=5, message = "must be greater than 5 characters")
    private String account;

    @Size(min=8, message = "must be longer greater than 8 characters")
    @NotNull(message = "password is required")
    private String password;

    @Size(min=8, message = "must be longer greater than 8 characters")
    @NotNull(message = "retype password is required")
    private String retypePassword;

    @NotNull(message = "password can't be empty")
    @Gmail
    private String email;

}

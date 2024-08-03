package com.techupdating.techupdating.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLoginDTO {

    @NotNull(message = "Account can not be empty")
    private String account;

    @NotNull(message = "Password can not be empty")
    private String password;

    private String emailCode;


}

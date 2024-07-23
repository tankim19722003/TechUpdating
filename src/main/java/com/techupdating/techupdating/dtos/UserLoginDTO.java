package com.techupdating.techupdating.dtos;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDTO {

    private String account;

    private String password;
}

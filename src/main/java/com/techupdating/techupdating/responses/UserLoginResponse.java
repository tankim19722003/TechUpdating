package com.techupdating.techupdating.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginResponse {

    private int id;

    private String fullname;

    private String avatar;

    private String account;

    private String email;

    private boolean isTwoWaysSecurityEnabled;
}

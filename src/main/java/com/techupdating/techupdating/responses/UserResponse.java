package com.techupdating.techupdating.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private int id;

    private String fullname;

    private String avatar;

}

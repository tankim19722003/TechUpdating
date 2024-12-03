package com.techupdating.techupdating.responses;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@NotEmpty
@Builder
public class UserTwoWayResponse {

    private int id;
    private boolean twoWaysSecurityEnabled;
}

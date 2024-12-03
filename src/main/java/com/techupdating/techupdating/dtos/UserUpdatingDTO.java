package com.techupdating.techupdating.dtos;

import com.techupdating.techupdating.validation.Gmail;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdatingDTO {
    private int id;

    private String fullname;

    @NotEmpty(message = "email can't be empty")
    @Gmail
    private String email;
}

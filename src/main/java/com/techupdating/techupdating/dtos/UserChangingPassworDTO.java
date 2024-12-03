package com.techupdating.techupdating.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserChangingPassworDTO {

    @JsonProperty("old_password")
    @NotEmpty(message = "Old password can't be empty")
    private String oldPassword;

    @JsonProperty("new_password")
    @NotEmpty(message = "New password can't be empty")
    private String newPassword;

    @JsonProperty("retype_password")
    @NotEmpty(message = "Retype password can't be empty")
    private String retypePassword;


}

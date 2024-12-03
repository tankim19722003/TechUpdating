package com.techupdating.techupdating.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentUpdatingDTO {

    @Min(value=1, message = "Comment id need greater than 0")
    private Integer id;

    @JsonProperty("user_id")
    @Min(value = 1, message = "User id need greater than 0")
    private Integer userId;

    @NotEmpty(message = "Content can't be empty")
    private String content;

}

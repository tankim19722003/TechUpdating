package com.techupdating.techupdating.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {

    @JsonProperty("user_id")
    @Positive(message = "User's id can not be empty")
    private int userId;

    @JsonProperty("post_id")
    @Positive(message = "Post's id can not be empty")
    private int postId;

    @JsonProperty("content")
    @NotEmpty(message = "Comment can't be empty")
    private String content;
}

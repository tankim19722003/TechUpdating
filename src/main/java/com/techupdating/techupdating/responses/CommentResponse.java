package com.techupdating.techupdating.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {

    private int id;

    private UserResponse userResponse;

    private String content;

    @JsonProperty("days_difference")
    private int daysDifference;

    @JsonProperty("months_difference")
    private int monthsDifference;

    @JsonProperty("years_difference")
    private int yearsDifference;

}

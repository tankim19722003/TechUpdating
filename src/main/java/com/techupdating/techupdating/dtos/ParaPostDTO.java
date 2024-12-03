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
public class ParaPostDTO {
    @JsonProperty("title")
    @NotEmpty(message = "Para's title can't be empty")
    private String title;

    @JsonProperty("content")
    @NotEmpty(message = "Para's content can't be empty")
    private String content;
}

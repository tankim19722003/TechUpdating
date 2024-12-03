package com.techupdating.techupdating.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContentPostCreatingDTO {

    @NotEmpty(message = "Title can't be empty!!")
    @JsonProperty("title")
    private String title;

    @NotEmpty(message = "Short description can't be empty")
    @JsonProperty("short_description")
    private String shortDescription;

    @JsonProperty("para_list")
    private List<ParaPostDTO> paraPostDTOList;
}

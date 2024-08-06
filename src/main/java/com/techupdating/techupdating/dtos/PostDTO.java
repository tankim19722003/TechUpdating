package com.techupdating.techupdating.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {

    private String title;

    private String shortDescription;

    @JsonProperty("user_id")
    private int userId;

    @JsonProperty("course_id")
    private int courseId;

    private List<PartDTO> parts;

}

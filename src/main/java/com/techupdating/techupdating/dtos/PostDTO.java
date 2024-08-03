package com.techupdating.techupdating.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PostDTO {

    private int id;
    private String title;

    @JsonProperty("short_description")
    private String shortDescription;

    @JsonProperty("user_id")
    private int userId;

    @JsonProperty("course_id")
    private int courseId;

}

package com.techupdating.techupdating.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseResponse {

    @JsonProperty("id")
    private int courseId;

    @JsonProperty("course_name")
    private String courseName;

    private int price;

    @JsonProperty("short_description")
    private String shortDescription;

    @JsonProperty("quantity_of_user")
    private int quantityOfUser;

    @JsonProperty("created_at")
    private Date createdAt;

    @JsonProperty("updated_at")
    private Date updatedAt;

}

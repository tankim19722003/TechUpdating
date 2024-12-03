package com.techupdating.techupdating.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostCreatingDTO {

    @JsonProperty("course_id")
    private int courseId;

    @JsonProperty("topic_id")
    private int topicId;

    @JsonProperty("content_post_creating")
    private ContentPostCreatingDTO contentPostCreatingDTO;
}

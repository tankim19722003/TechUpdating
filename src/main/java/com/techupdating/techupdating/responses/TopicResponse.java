package com.techupdating.techupdating.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopicResponse {

    @JsonProperty("id")
    private int id;

    @JsonProperty("title_name")
    private String titleName;

    @JsonProperty("post_responses")
    private List<PostResponseInTopic> postResponses;
}

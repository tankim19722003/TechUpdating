package com.techupdating.techupdating.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.techupdating.techupdating.models.Part;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {

    private int id;

    private String title;

    @JsonProperty("short_description")
    private String shortDescription;

    @JsonProperty("created_at")
    private Date createdAt;

    @JsonProperty("updated_at")
    private  Date updatedAt;

    @JsonProperty("quantity_of_like")
    private Integer quantityOfLike;

    @JsonProperty("post_view")
    private Integer postView;

    @JsonProperty("thumbnail")
    private String thumbnail;

    @JsonProperty("parts")
    private List<PartResponse> parts;

}

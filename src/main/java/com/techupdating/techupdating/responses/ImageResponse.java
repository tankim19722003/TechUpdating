package com.techupdating.techupdating.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ImageResponse {

    @JsonProperty("id")
    private int id;

    @JsonProperty("url_image")
    private String urlImage;
}

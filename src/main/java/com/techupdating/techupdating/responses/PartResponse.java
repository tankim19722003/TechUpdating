package com.techupdating.techupdating.responses;

import com.techupdating.techupdating.models.Image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PartResponse {

    private int id;

    private String title;

    private String content;

    private List<ImageResponse> imageResponses;
}

package com.techupdating.techupdating.responses;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostSelectResponse {

    private int id;
    private String title;
}

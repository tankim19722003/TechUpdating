package com.techupdating.techupdating.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reponse {

    @JsonProperty("name")
    private String name;

    @JsonProperty("message")
    private String message;
}


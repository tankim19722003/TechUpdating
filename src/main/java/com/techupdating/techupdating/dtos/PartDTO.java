package com.techupdating.techupdating.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PartDTO {

    private String titlePart;

    private String content;

    private MultipartFile image;

}

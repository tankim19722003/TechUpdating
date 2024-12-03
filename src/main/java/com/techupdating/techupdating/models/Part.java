package com.techupdating.techupdating.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.techupdating.techupdating.responses.ImageResponse;
import com.techupdating.techupdating.responses.PartResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "part")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Part {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {
            CascadeType.DETACH, CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE
    })
    @JsonBackReference
    private Post post;

    @OneToMany(mappedBy = "part", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Image> images;

    public PartResponse toPartResponse() {


        List<ImageResponse> imageResponses = new ArrayList<>();
        if(images != null) {
            imageResponses = images.stream().map(image -> image.toImageResponse()).toList();
        }

        // return part response
        return PartResponse.builder()
                .id(getId())
                .title(getTitle())
                .imageResponses(imageResponses)
                .content(getContent())
                .build();
    }
}


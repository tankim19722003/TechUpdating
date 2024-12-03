package com.techupdating.techupdating.models;

import com.techupdating.techupdating.responses.ImageResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "image")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "url_image")
    private String urlImage;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {
            CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH
    })
    @JoinColumn(name = "part_id")
    private Part part;

    public ImageResponse toImageResponse() {
        return ImageResponse.builder()
                .id(this.id)
                .urlImage(this.urlImage)
                .build();
    }
}

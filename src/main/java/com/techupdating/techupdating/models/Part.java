package com.techupdating.techupdating.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Post post;

    @OneToMany(mappedBy = "part", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Image> image;
}


package com.techupdating.techupdating.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.techupdating.techupdating.responses.PartResponse;
import com.techupdating.techupdating.responses.PostResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "post")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "short_description")
    private String shortDescription;

    @Column(name = "thumbnail")
    private String thumbnail;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private  Date updatedAt;

    @Column(name = "quantity_of_like")
    private Integer quantityOfLike;

    @Column(name = "post_view")
    private Integer postView;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {
            CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH }
    )
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "course_id")
    @JsonBackReference
    private Course course;

    @OneToMany(mappedBy = "post",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Part> parts;

    @OneToMany(mappedBy = "post",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Comment> comments;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {
            CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH
    })

    @JoinColumn(name = "topic_id")
    private PostTopic postTopic;


    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
        updatedAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }

    public PostResponse toPostResponse() {

        List<PartResponse> partResponses = new ArrayList<>();
        // convert part to part response
        if (getParts() != null) {
              partResponses = getParts().stream().map( partItem -> {
                return partItem.toPartResponse();
            }).toList();
        }

        return PostResponse.builder()
                .id(getId())
                .title(getTitle())
                .thumbnail(getThumbnail())
                .shortDescription(getShortDescription())
                .postView(getPostView())
                .quantityOfLike(getQuantityOfLike())
                .createdAt(getCreatedAt())
                .updatedAt(getUpdatedAt())
                .parts(partResponses)
                .build();
    }
}

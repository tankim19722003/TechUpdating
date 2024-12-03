package com.techupdating.techupdating.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.techupdating.techupdating.responses.CommentResponse;
import com.techupdating.techupdating.responses.UserResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

@Table(name = "comment")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(cascade = {
            CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH, CascadeType.PERSIST
    })
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;


    @ManyToOne(cascade = {
            CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH, CascadeType.PERSIST
    })
    @JsonBackReference
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(name = "content")
    private String content;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public static CommentResponse toCommentResponse(Comment comment) {

        UserResponse userResponse = UserResponse.builder()
                .id(comment.getUser().getId())
                .avatar(comment.getUser().getAvatar())
                .fullname(comment.getUser().getFullname())
                .build();

        return CommentResponse.builder()
                .id(comment.getId())
                .daysDifference((int) ChronoUnit.DAYS.between(comment.getCreatedAt(), LocalDateTime.now()))
                .monthsDifference((int) ChronoUnit.MONTHS.between(comment.getCreatedAt(), LocalDateTime.now()))
                .yearsDifference((int) ChronoUnit.YEARS.between(comment.getCreatedAt(), LocalDateTime.now()))
                .content(comment.getContent())
                .userResponse(userResponse)
                .build();
    }


}

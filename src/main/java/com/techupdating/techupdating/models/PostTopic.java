package com.techupdating.techupdating.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.techupdating.techupdating.responses.PostResponseInTopic;
import com.techupdating.techupdating.responses.TopicResponse;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "post_topic")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostTopic {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "topic_name")
    private String topicName;


    @OneToMany(fetch = FetchType.LAZY,mappedBy = "postTopic", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Post> posts;


    @ManyToOne(fetch = FetchType.EAGER, cascade = {
            CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH
    })
    @JsonBackReference
    @JoinColumn(name = "course_id")
    private Course course;

    @Override
    public String toString() {
        return "PostTopic{" +
                "id=" + id +
                ", topicName='" + topicName + '\'' +
                '}';
    }

    public TopicResponse toTopicResponse() {

        // convert post to post reponse
        List<PostResponseInTopic> postResponses = getPosts().stream().map(post -> {
            return PostResponseInTopic.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .build();
        }).toList();

        // return post response
        return TopicResponse.builder()
                .titleName(getTopicName())
                .postResponses(postResponses)
                .build();

    }
}

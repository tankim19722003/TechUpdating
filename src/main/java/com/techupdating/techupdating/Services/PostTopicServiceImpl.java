package com.techupdating.techupdating.Services;

import com.techupdating.techupdating.daos.PostDAO;
import com.techupdating.techupdating.models.PostTopic;
import com.techupdating.techupdating.repositories.CourseRepository;
import com.techupdating.techupdating.repositories.PostTopicRepository;
import com.techupdating.techupdating.responses.PostResponseInTopic;
import com.techupdating.techupdating.responses.TopicResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostTopicServiceImpl implements  PostTopicService{

    private final CourseRepository courseRepository;
    private final PostTopicRepository postTopicRepository;
    private final PostDAO postDAO;

    @Override
    public List<TopicResponse> getTopicsResponseByCourseId(int courseId) {
        //List of topic response return
        List<TopicResponse> topicResponseReturning = new ArrayList<>();

        // check course existing
        boolean isCourseExisting = courseRepository.existsById(courseId);
        if (!isCourseExisting) throw new RuntimeException("Course does not exist");

        // get all topic of course
        List<PostTopic> postTopics = postTopicRepository.findAllPostByCourseId(courseId).orElseThrow(
                () ->  new RuntimeException("Top does not exist")
        );

        // get all title and id belongs to topic
        for (PostTopic topic : postTopics) {

            // create topic response : name of topic, list of title and id post response
            TopicResponse topicResponse = new TopicResponse();
            topicResponse.setTitleName(topic.getTopicName());

            List<Object[]> postResponseObj = postDAO.findPostIdAndTitleByTopicId(topic.getId());

            // create list of post response
            List<PostResponseInTopic> postsResponse = new ArrayList<>();

            for (Object[] post : postResponseObj) {

                PostResponseInTopic postResponse = PostResponseInTopic.builder()
                        .id((int)post[0])
                        .title((String)post[1])
                        .build();
               postsResponse.add(postResponse);
            }

            // add list to topic response
            topicResponse.setPostResponses(postsResponse);

            // add each topic response to topic returning
            topicResponseReturning.add(topicResponse);
        }

        // return topic response
        return topicResponseReturning;
    }

    public List<TopicResponse> findPostGroups(int courseId) {

        // check course existing
        boolean isCourseExisting = courseRepository.existsById(courseId);
        if (!isCourseExisting) throw new RuntimeException("Course does not exist");

        //List of topic response return
        List<TopicResponse> topicResponseReturning = new ArrayList<>();
        List <PostTopic> postTopics= postTopicRepository.findAllPostByCourseId(courseId).orElseThrow(
                () -> new RuntimeException("Post Group does not exist")
        );

        topicResponseReturning = postTopics.stream().map(postGroupItem -> {
                return TopicResponse.builder()
                        .id(postGroupItem.getId())
                        .titleName(postGroupItem.getTopicName())
                        .build();
            }).toList();

        return topicResponseReturning;
    }
}

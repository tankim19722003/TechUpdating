package com.techupdating.techupdating.Services;

import com.techupdating.techupdating.daos.PostTopicDAO;
import com.techupdating.techupdating.models.*;
import com.techupdating.techupdating.repositories.*;
import com.techupdating.techupdating.responses.CourseResponse;
import com.techupdating.techupdating.responses.PostResponse;
import com.techupdating.techupdating.responses.PostResponseInTopic;
import com.techupdating.techupdating.responses.TopicResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceimpl implements CourseService{
    private final CourseRepository courseRepository;
    private final CourseRegistrationRepository courseRegistrationRepository;
    private final UserRepository userRepository;
    private final PostTopicRepository postTopicRepository;
    private final PostTopicDAO postTopicDAO;
    private final PostRepository postRepository;

    @Override
    public List<CourseResponse> findCoursesByLanguageId(int languageId) {
        List<CourseResponse>  courseResponses =  courseRepository.findCourseByLanguageId(languageId).stream().map(
                course -> {
                    // query to get quantity of user from user and course
                    int quantityUserEnrolled = courseRegistrationRepository.getQuantityUserRegistered(course.getId());
                    return CourseResponse.builder()
                            .courseName(course.getCourseName())
                            .courseId(course.getId())
                            .shortDescription(course.getShortDescription())
                            .price(course.getPrice())
                            .quantityOfUser(quantityUserEnrolled)
                            .createdAt(course.getCreatedAt())
                            .updatedAt(course.getUpdatedAt())
                            .build();
                }).toList();
        return courseResponses;
    }

    @Override
    public CourseResponse findCourseById(int courseId) {

        Course course = courseRepository.findById(courseId).orElseThrow(
                () -> new RuntimeException("Course does not found")
        );

        // get quantity of user enroll
        int quantityOfUserEnrolled = courseRegistrationRepository.getQuantityUserRegistered(course.getId());

        return CourseResponse.builder()
                .courseId(course.getId())
                .courseName(course.getCourseName())
                .quantityOfUser(quantityOfUserEnrolled)
                .shortDescription(course.getShortDescription())
                .createdAt((LocalDate)course.getCreatedAt())
                .updatedAt(course.getUpdatedAt())
                .price(course.getPrice())
                .imageName(course.getImageName())
                .build();
    }

    @Override
    public boolean checkUserEnrollCourse(int userId, int courseId) {
        return courseRegistrationRepository.existsByUserIdAndCourseId(userId, courseId);
    }

    @Override
    @Transactional
    public int registerCourse(int userId, int courseId) {

        // result > 1 course not free < 1 free
        int result = 0;

        Course course = courseRepository.findById(courseId).orElseThrow(
                () ->  new RuntimeException("Course does not exist!")
        );

        User user = userRepository.findById(userId).orElseThrow(
                () ->  new RuntimeException("User does not found")
        );

        // create CoureRegisteration
        CourseRegistration courseRegistration = CourseRegistration.builder()
                .user(user)
                .enrollDate(LocalDate.now())
                .build();

        // course need money
        if (course.getPrice() > 0) {
            courseRegistration.setEnabled(false);
            result = -1;
        } else {
            courseRegistration.setEnabled(true);
            result = 1;

        }

        // set course to register course
        courseRegistration.setCourse(course);

        // save user register course
        courseRegistrationRepository.save(courseRegistration);

        return result;
    }

    @Override
    public CourseRegistration findUserCourseRegistration(int userId, int courseId) {
        return courseRegistrationRepository
                .findRegistrationByUserIdAndCourseId(userId, courseId);
    }

    @Override
    public List<TopicResponse> findAllTopicsResponse(int courseId, int userId) {

        // check user register course
        boolean isUserRegisterCourse = courseRegistrationRepository.existsByUserIdAndCourseId(userId, courseId);
        if (!isUserRegisterCourse) {
            return null;
        }

        // get topic
        List<PostTopic> postTopics = postTopicRepository.findAllPostByCourseId(courseId).orElseThrow(
                () ->  new RuntimeException("Topic does not found")
        );

        // convert list topic to list topic response
        List<TopicResponse> topicResponses = postTopics.stream().map(postTopic -> {

            TopicResponse topicResponse = new TopicResponse();

            topicResponse.setTitleName(postTopic.getTopicName());

            // convert post to post response
            topicResponse.setPostResponses(
                    postTopic.getPosts().stream().map(post -> {
                        return PostResponseInTopic.builder()
                                .id(post.getId())
                                .title(post.getTitle())
                                .build();
                    }).toList()
            );

            return topicResponse;
        }).toList();

        return topicResponses;
    }

    @Override
    public List<TopicResponse> findAllTopicsResponseWithJoinFetch(int courseId, int userId) {
        return postTopicDAO.findAllTopicWithJoinFetch(userId, courseId);
    }

    @Override
    public PostResponse findPostById(int postId) {

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new RuntimeException("Post does not found")
        );

        // convert post to post response
        PostResponse postResponse = post.toPostResponse();

        return postResponse;
    }

}

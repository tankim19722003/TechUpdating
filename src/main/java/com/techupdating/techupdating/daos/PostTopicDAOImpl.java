package com.techupdating.techupdating.daos;

import com.techupdating.techupdating.models.PostTopic;
import com.techupdating.techupdating.repositories.CourseRegistrationRepository;
import com.techupdating.techupdating.responses.TopicResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PostTopicDAOImpl implements PostTopicDAO {

    private EntityManager entityManager;
    private CourseRegistrationRepository courseRegistrationRepository;

    @Autowired
    public PostTopicDAOImpl(
            EntityManager entityManager,
            CourseRegistrationRepository courseRegistrationRepository
    ) {
        this.entityManager = entityManager;
        this.courseRegistrationRepository = courseRegistrationRepository;
    }

    @Override
    public List<TopicResponse> findAllTopicWithJoinFetch(int userId, int courseId) {

        // check user register course
        boolean isUserRegisterCourse = courseRegistrationRepository.existsByUserIdAndCourseId(userId, courseId);
        if (!isUserRegisterCourse) throw new RuntimeException("User need to register course!");

        // create query
        TypedQuery<PostTopic> theQuery = entityManager.createQuery(
                "select pt from PostTopic pt " +
                    "JOIN FETCH pt.posts " +
                        "where pt.course.id = :courseId", PostTopic.class
        );
        theQuery.setParameter("courseId" , courseId);

        // execute query
        List<PostTopic> postTopics = theQuery.getResultList();

        // convert it to post topic response
        List<TopicResponse> topicResponses = postTopics.stream().map(topic -> {
            return topic.toTopicResponse();
        }).toList();

        return topicResponses;
    }
}

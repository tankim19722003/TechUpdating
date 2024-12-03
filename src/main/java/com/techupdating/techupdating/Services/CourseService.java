package com.techupdating.techupdating.Services;


import com.techupdating.techupdating.models.CourseRegistration;
import com.techupdating.techupdating.responses.CourseResponse;
import com.techupdating.techupdating.responses.PostResponse;
import com.techupdating.techupdating.responses.TopicResponse;

import java.util.List;

public interface CourseService {
    List<CourseResponse> findCoursesByLanguageId(int languageId);

    CourseResponse findCourseById(int courseId);

    boolean checkUserEnrollCourse(int userId, int courseId);

    int registerCourse(int userId,int courseId);

    CourseRegistration findUserCourseRegistration(int userId, int courseId);

    List<TopicResponse> findAllTopicsResponse(int courseId, int userId);

    List<TopicResponse> findAllTopicsResponseWithJoinFetch(int courseId, int userId);

    PostResponse findPostById(int postId);

}

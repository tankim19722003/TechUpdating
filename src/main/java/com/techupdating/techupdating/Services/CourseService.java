package com.techupdating.techupdating.Services;


import com.techupdating.techupdating.models.CourseRegistration;
import com.techupdating.techupdating.responses.CourseResponse;

import java.util.List;

public interface CourseService {
    List<CourseResponse> findCoursesByLanguageId(int languageId);

    CourseResponse findCourseById(int courseId);

    boolean checkUserEnrollCourse(int userId, int courseId);

    int registerCourse(int userId,int courseId);

    CourseRegistration findUserCourseRegistration(int userId, int courseId);
}

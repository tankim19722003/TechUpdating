package com.techupdating.techupdating.Services;


import com.techupdating.techupdating.responses.CourseResponse;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    List<CourseResponse> findCoursesByLanguageId(int languageId);

    CourseResponse findCourseById(int courseId);

}

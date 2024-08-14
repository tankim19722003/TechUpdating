package com.techupdating.techupdating.Services;


import com.techupdating.techupdating.responses.CourseResponse;

import java.util.List;

public interface CourseService {
    List<CourseResponse> findCoursesByLanguageId(int languageId);

}

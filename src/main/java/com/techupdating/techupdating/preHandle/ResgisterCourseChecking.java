package com.techupdating.techupdating.preHandle;

import com.techupdating.techupdating.repositories.CourseRegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ResgisterCourseChecking {

    private static CourseRegistrationRepository courseRegistrationRepository;

    @Autowired
    public ResgisterCourseChecking(CourseRegistrationRepository courseRegistrationRepository) {
        this.courseRegistrationRepository = courseRegistrationRepository;
    }

    public static boolean checkRegisterdPost(int userId, int courseId) {

        return courseRegistrationRepository.existsByUserIdAndCourseId(userId, courseId);
    }
}

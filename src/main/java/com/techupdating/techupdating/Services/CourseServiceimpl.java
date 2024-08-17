package com.techupdating.techupdating.Services;

import com.techupdating.techupdating.models.Course;
import com.techupdating.techupdating.models.CourseRegistration;
import com.techupdating.techupdating.models.User;
import com.techupdating.techupdating.repositories.CourseRegistrationRepository;
import com.techupdating.techupdating.repositories.CourseRepository;
import com.techupdating.techupdating.repositories.UserRepository;
import com.techupdating.techupdating.responses.CoursePageResponse;
import com.techupdating.techupdating.responses.CourseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseServiceimpl implements CourseService{
    private final CourseRepository courseRepository;
    private final CourseRegistrationRepository courseRegistrationRepository;
    private final UserRepository userRepository;
    @Override
    public List<CourseResponse> findCoursesByLanguageId(int languageId) {
        List<CourseResponse>  courseResponses =  courseRepository.findCourseByLanguageId(languageId).stream().map(
                course -> {
                    return CourseResponse.builder()
                            .courseName(course.getCourseName())
                            .courseId(course.getId())
                            .shortDescription(course.getShortDescription())
                            .price(course.getPrice())
                            .quantityOfUser(course.getQuantityOfUser())
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

        return CourseResponse.builder()
                .courseId(course.getId())
                .courseName(course.getCourseName())
                .quantityOfUser(course.getQuantityOfUser())
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

            // increase quantity of user
            int quantityOfUser = course.getQuantityOfUser() + 1;
            course.setQuantityOfUser(quantityOfUser);
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
}

package com.techupdating.techupdating.Services;

import com.techupdating.techupdating.models.Course;
import com.techupdating.techupdating.repositories.CourseRepository;
import com.techupdating.techupdating.responses.CoursePageResponse;
import com.techupdating.techupdating.responses.CourseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseServiceimpl implements CourseService{
    private final CourseRepository courseRepository;

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
                .createdAt(course.getCreatedAt())
                .updatedAt(course.getUpdatedAt())
                .price(course.getPrice())
                .build();
    }


}

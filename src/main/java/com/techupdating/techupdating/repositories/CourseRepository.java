package com.techupdating.techupdating.repositories;

import com.techupdating.techupdating.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Integer> {

    List<Course> findCourseByLanguageId(int id);

}

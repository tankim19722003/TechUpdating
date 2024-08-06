package com.techupdating.techupdating.repositories;

import com.techupdating.techupdating.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Integer> {
}

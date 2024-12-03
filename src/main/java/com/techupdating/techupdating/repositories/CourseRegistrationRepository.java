package com.techupdating.techupdating.repositories;

import com.techupdating.techupdating.models.CourseRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface
CourseRegistrationRepository extends JpaRepository<CourseRegistration, Integer> {


    @Query("SELECT CASE WHEN COUNT(cr) > 0 THEN TRUE ELSE FALSE END FROM CourseRegistration cr WHERE cr.user.id = :userId AND cr.course.id = :courseId")
    boolean existsByUserIdAndCourseId(
            @Param("userId") int userId,
            @Param("courseId") int courseId
    );


    @Query("SELECT cr FROM CourseRegistration cr WHERE cr.user.id = :userId AND cr.course.id = :courseId")
    CourseRegistration findRegistrationByUserIdAndCourseId(
            @Param("userId") int userId,
            @Param("courseId") int courseId
    );

    @Query("Select count(cr) from CourseRegistration cr where cr.course.id = :courseId and cr.enabled = true")
    int getQuantityUserRegistered(
            @Param("courseId") int courseId
    );
}

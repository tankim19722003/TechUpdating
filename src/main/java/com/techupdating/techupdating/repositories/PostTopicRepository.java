package com.techupdating.techupdating.repositories;

import com.techupdating.techupdating.models.PostTopic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostTopicRepository extends JpaRepository<PostTopic, Integer> {

    @Query("select pt from PostTopic pt where pt.course.id = :courseId")
    Optional<List<PostTopic>> findAllPostByCourseId(@Param("courseId") int courseId);

}

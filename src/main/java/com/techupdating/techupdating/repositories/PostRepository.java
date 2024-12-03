package com.techupdating.techupdating.repositories;

import com.techupdating.techupdating.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {

    List<Post> findAllByCourseId(int courseId);

}

package com.techupdating.techupdating.repositories;

import com.techupdating.techupdating.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
}

package com.techupdating.techupdating.repositories;

import com.techupdating.techupdating.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    @Query("select c from Comment c where c.post.id= :postId ORDER BY c.createdAt DESC")
    List<Comment> findAllCommentsByPostId(@Param("postId") int postId);

    @Modifying
    @Query("delete from Comment c where c.id = :commentId")
    void deleteCommentById(@Param("commentId") int commentId);
}

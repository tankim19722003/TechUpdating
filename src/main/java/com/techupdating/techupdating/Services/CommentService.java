package com.techupdating.techupdating.Services;

import com.techupdating.techupdating.dtos.CommentDTO;
import com.techupdating.techupdating.dtos.CommentUpdatingDTO;
import com.techupdating.techupdating.responses.CommentResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {

    List<CommentResponse> findAllCommentsByPostId(int postId);

    CommentResponse createComment(CommentDTO commentDTO);

    void deleteCommentById(int commentId);

    CommentResponse updateComment(CommentUpdatingDTO commentUpdatingDTO);
}

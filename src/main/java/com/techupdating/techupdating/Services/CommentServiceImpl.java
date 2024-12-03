package com.techupdating.techupdating.Services;

import com.techupdating.techupdating.dtos.CommentDTO;
import com.techupdating.techupdating.dtos.CommentUpdatingDTO;
import com.techupdating.techupdating.models.Comment;
import com.techupdating.techupdating.models.Post;
import com.techupdating.techupdating.models.User;
import com.techupdating.techupdating.repositories.CommentRepository;
import com.techupdating.techupdating.repositories.PostRepository;
import com.techupdating.techupdating.repositories.UserRepository;
import com.techupdating.techupdating.responses.CommentResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Override
    public List<CommentResponse> findAllCommentsByPostId(int postId) {

        // check post existing
        boolean isExistingPost = postRepository.existsById(postId);
        if (!isExistingPost) throw new RuntimeException("Post does not exist");

        List<Comment> comments = commentRepository.findAllCommentsByPostId(postId);

        if (!comments.isEmpty()) {
            List<CommentResponse> commentResponses = comments.stream().map(comment -> {
                return Comment.toCommentResponse(comment);
            }).toList();

            return commentResponses;
        } else {
            throw new RuntimeException("Comment not found");
        }

    }

    @Override
    @Transactional
    public CommentResponse createComment(CommentDTO commentDTO) {

        // check user existing
        User user = userRepository.findById(commentDTO.getUserId()).orElseThrow(
                () ->  new RuntimeException("User does not found")
        );
        // check post existing
        Post post = postRepository.findById(commentDTO.getPostId()).orElseThrow(
                () -> new RuntimeException("Post does not found")
        );

        // save comment
        Comment comment = Comment.builder()
                .createdAt(LocalDateTime.now())
                .user(user)
                .post(post)
                .content(commentDTO.getContent())
                .build();

        comment = commentRepository.save(comment);

        return Comment.toCommentResponse(comment);

    }

    @Override
    @Transactional
    public void deleteCommentById(int commentId) {
        if (commentRepository.existsById(commentId)) {
            commentRepository.deleteCommentById(commentId);
        } else {
            throw new EntityNotFoundException("Comment with ID " + commentId + " does not exist.");
        }
    }

    @Override
    public CommentResponse updateComment(CommentUpdatingDTO commentUpdatingDTO) {

        // check user existing
        boolean isUserExisting = userRepository.existsById(commentUpdatingDTO.getUserId());
        if (!isUserExisting) {
            throw new RuntimeException("User does not exist!!");
        }
        // check if user's comment
        Comment comment = commentRepository.findById(commentUpdatingDTO.getId()).orElseThrow(
                () -> new RuntimeException("Comment does not exist!!")
        );


        // check post's user
        if (comment.getUser().getId() != commentUpdatingDTO.getUserId()) {
            throw new RuntimeException("Invalid User");
        }

        // change content of post
        comment.setContent(commentUpdatingDTO.getContent());

        // save changing on comment and return comment response

        commentRepository.save(comment);

        CommentResponse commentResponse = Comment.toCommentResponse(comment);

        return commentResponse;

    }

}

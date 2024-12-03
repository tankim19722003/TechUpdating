package com.techupdating.techupdating.controllers.commons;

import com.techupdating.techupdating.Services.CommentService;
import com.techupdating.techupdating.dtos.CommentDTO;
import com.techupdating.techupdating.dtos.CommentUpdatingDTO;
import com.techupdating.techupdating.responses.CommentResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.prefix}/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/create_comment")
    public ResponseEntity<?> createComment(
            @Valid @RequestBody CommentDTO commentDTO,
            BindingResult result
    ) {

        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .collect(Collectors.toList());

                System.out.println(errorMessages);
                return ResponseEntity.badRequest().body(errorMessages);
            }

            return ResponseEntity.ok().body(commentService.createComment(commentDTO));
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/find_all_comments/{postId}")
    public ResponseEntity<?> findAllComments(
            @PathVariable("postId") int postId
    ) {
        try {
            return ResponseEntity.ok(commentService.findAllCommentsByPostId(postId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{commentId}")
    public void deleteCommentById(
            @PathVariable("commentId") int commentId
    ) {
        commentService.deleteCommentById(commentId);
    }

    @PutMapping("/update_comment")
    public ResponseEntity<?> updateComment(
            @Valid @RequestBody CommentUpdatingDTO commentUpdatingDTO,
            BindingResult result
    ) {
        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .collect(Collectors.toList());

                System.out.println(errorMessages);
                return ResponseEntity.badRequest().body(errorMessages);
            }

            return ResponseEntity.ok().body(commentService.updateComment(commentUpdatingDTO));
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

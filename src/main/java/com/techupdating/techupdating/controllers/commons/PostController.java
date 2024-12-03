package com.techupdating.techupdating.controllers.commons;

import com.techupdating.techupdating.Services.PostService;
import com.techupdating.techupdating.dtos.PostCreatingDTO;
import com.techupdating.techupdating.dtos.PostDTO;
import com.techupdating.techupdating.preHandle.PreTopic;
import com.techupdating.techupdating.preHandle.ResgisterCourseChecking;
import com.techupdating.techupdating.responses.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Controller
@RequestMapping("${api.prefix}/post")
@RequiredArgsConstructor
public class PostController {

    @Value("${api.prefix}")
    private String api;

    private final PostService postService;

    Logger logger = Logger.getLogger(this.getClass().getName());
//    @GetMapping("/show_post_creating")
//    public String showPostCreating(
//    ) {
//
//        return "Admin/post-creating";
//    }

//    @PostMapping(path = "/create_post", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
//    public ResponseEntity<?> postController(
//            @ModelAttribute("postDTO") PostDTO postDTO
//    ) {
////        System.out.println(postDTO);
////        System.out.println("This is data that i have send: "+ postDTO);
//        try {
//            logger.info("Post DTO: " + postDTO);
//            return ResponseEntity.ok(postService.createPost(postDTO));
//        } catch(Exception e) {
//            logger.warning("error: "+ e.getMessage());
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }

    @GetMapping("/show-post-page")
    public String showPostPage(
            Model model,
            @RequestParam("course_id") int courseId,
            @RequestParam("user_id") int userId
    ) {

        // user id need to load from authentication
//        CourseRegistration courseRegistration = postService.findAllPosts(userId, courseId);


        return "/User/post-page";

    }

    @GetMapping("/get_post/{post_id}")
    public String getPostById(
            @PathVariable("postId") int postId,
            Model model
    ) {

        try {
            PostResponse postResponse =  postService.findPostById(postId);
            model.addAttribute("postResponse", postResponse);
        } catch(Exception e) {
            System.out.println("error: " + e.getMessage());
            model.addAttribute("postError", e.getMessage());
        }

        return "/User/post-page";
    }

    @GetMapping("/show_post/{post_id}")
    public String showPostById(
            @PathVariable("post_id") int postId,
            @RequestParam("user_id") int userId,
            @RequestParam("course_id") int courseId,
            Model model
    ) {

        System.out.println("Handling show post...");
        // check user register course
        boolean isRegistered =  ResgisterCourseChecking.checkRegisterdPost(userId, courseId);
        if (!isRegistered) {
            model.addAttribute("error","Your account need to be register course");
            return "/User/error";
        }

       try {
           PostResponse postResponse = postService.findPostById(postId);
           String getPostApi = api + "/post/show_post";
           model.addAttribute("postResponse", postResponse);
           model.addAttribute("api",getPostApi);

           // get all topic to display the side bar
           List<TopicResponse> topicResponses = PreTopic.getListTopicsResponse(model ,userId, courseId);

           model.addAttribute("topicResponse",topicResponses);


           // load comment for post
          List<CommentResponse> commentResponses = postService.findAllCommentsByPostId(postId);
          model.addAttribute("commentResponses", commentResponses);

           return "/User/post-page";

       } catch (Exception e) {
           model.addAttribute("error",e.getMessage());
           return "/User/error";
        }

    }


    @PostMapping("/create_post/{user_id}")
    public ResponseEntity<?> createPost(
            @PathVariable("user_id") int userId,
            @RequestBody PostCreatingDTO postCreatingDTO,
            BindingResult result
    ) {

        if (result.hasErrors()) {
            List<ErrorResponse> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(fieldError -> ErrorResponse.builder()
                            .name(fieldError.getField())  // Set the field name
                            .error(fieldError.getDefaultMessage())  // Set the error message if needed
                            .build())
                    .collect(Collectors.toList());

            System.out.println(errorMessages);
            return ResponseEntity.badRequest().body(errorMessages);
        }

        try {
            PostResponse postResponse =  postService.createPost(userId, postCreatingDTO);
            return ResponseEntity.ok().body(postResponse);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(
                    ErrorResponse.builder()
                            .name(ErrorResponse.ERROR)
                            .error(e.getMessage())
                            .build()
            );
        }

    }

    @GetMapping("/show-post-creating")
    public String showPostCreating() {
        return "Admin/post-creating";
    }

    @GetMapping("/show_post_image_adding")
    public String showAddingImageToPostPage() {
        return "/Admin/post-image-page";
    }

    @GetMapping("/get_id_title_post/{course_id}")
    public ResponseEntity<?> getAllPostsByCourseId(
        @PathVariable("course_id") int courseId
    ) {

            List<PostSelectResponse> postResponses = postService.getIdAndTitlePostByCourseId(courseId);
            return ResponseEntity.ok().body(postResponses);
    }

    @GetMapping("/{post_id}")
    public ResponseEntity<?> findPostById(
            @PathVariable("post_id") int postId
    ) {

        try {
            PostResponse postResponse = postService.findPostById(postId);
            return ResponseEntity.ok().body(postResponse);

        } catch(Exception e) {
            return ResponseEntity.badRequest().body(
                    ErrorResponse.builder()
                            .error(e.getMessage())
                            .name(ErrorResponse.ERROR)
                            .build()
            );
        }

    }

}

package com.techupdating.techupdating.controllers.comons;

import com.techupdating.techupdating.Services.PostService;
import com.techupdating.techupdating.dtos.PostDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.logging.Logger;

@Controller
@RequestMapping("/api/v1/dev_updating/admin/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    Logger logger = Logger.getLogger(this.getClass().getName());
    @GetMapping("/show_post_creating")
    public String showPostCreating(
    ) {

        return "Admin/post-creating";
    }

    @PostMapping(path = "/create_post", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> postController(
            @ModelAttribute("postDTO") PostDTO postDTO
    ) {
//        System.out.println(postDTO);
//        System.out.println("This is data that i have send: "+ postDTO);
        try {
            logger.info("Post DTO: " + postDTO);
            return ResponseEntity.ok(postService.createPost(postDTO));
        } catch(Exception e) {
            logger.warning("error: "+ e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}

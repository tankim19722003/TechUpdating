package com.techupdating.techupdating.controllers.Admin;

import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/dev_updating/admin/post")
public class PostController {

    @PostMapping("/show_post_creating")
    public String showPostCreating(
    ) {
        return "/Admin/post-creating";
    }

    @PostMapping("/create_post")
    public ResponseEntity<String> postController() {
        return ResponseEntity.ok("Xin chào việt nam");
    }

}

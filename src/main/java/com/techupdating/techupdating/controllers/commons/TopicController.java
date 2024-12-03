package com.techupdating.techupdating.controllers.commons;

import com.techupdating.techupdating.Services.PostTopicService;
import com.techupdating.techupdating.responses.TopicResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/v1/dev_updating/topic")
@RequiredArgsConstructor
public class TopicController {
    private final PostTopicService postTopicService;

    @GetMapping("/{process_show_topic}")
    public void getAllTopicsThymeleaf(
            @PathVariable("courseId") int courseId,
            Model model
    ) {

            List<TopicResponse> topicResponses = postTopicService.getTopicsResponseByCourseId(courseId);
            model.addAttribute("topicResponse",topicResponses);
    }

    @GetMapping("/get_all_post_groups/{courseId}")
    public ResponseEntity<List<TopicResponse>> getdAllTopics(
            @PathVariable("courseId") int courseId
    ) {

        List<TopicResponse> topicResponses = postTopicService.findPostGroups(courseId);
        return ResponseEntity.ok().body(topicResponses);
    }
}

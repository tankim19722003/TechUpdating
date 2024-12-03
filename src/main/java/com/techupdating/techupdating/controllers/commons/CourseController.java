package com.techupdating.techupdating.controllers.commons;

import com.techupdating.techupdating.Services.CourseService;
import com.techupdating.techupdating.Services.PostService;
import com.techupdating.techupdating.models.CourseRegistration;
import com.techupdating.techupdating.responses.CourseResponse;
import com.techupdating.techupdating.responses.PostResponse;
import com.techupdating.techupdating.responses.TopicResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/api/v1/dev_updating/course")
@RequiredArgsConstructor
public class CourseController {

    @Value("${api.prefix}")
    private String api;

    private final PostService postService;

    Logger logger = Logger.getLogger(this.getClass().getName());
    private final CourseService courseService;

    @GetMapping("/{language_id}")
    public ResponseEntity<?> getCourseByLanguageId(
            @PathVariable("language_id") int languageId
    ) {

        return ResponseEntity.ok().body(
            courseService.findCoursesByLanguageId(languageId)
        );
    }

    @GetMapping("/show_course/{id}")
    public String showAllCourseByLanguageId (
        @PathVariable("id") int id,
        Model model
    ) {

        List<CourseResponse> courses = courseService.findCoursesByLanguageId(id);

        model.addAttribute("courses", courses);

        return "/User/course-page";
    }

    @GetMapping("/process_show_course")
    public String processShowCourse(
            @RequestParam("course_id") int courseId,
            @RequestParam("user_id") Integer userId,
            Model model
    ) {

        // find user registration
        CourseRegistration courseRegistration = courseService.findUserCourseRegistration(userId, courseId);

        boolean isEnrollCourse = courseRegistration != null;

        logger.info("course registration: " + courseRegistration);

        if (!isEnrollCourse || !courseRegistration.isEnabled()) {
            model.addAttribute("courseId", courseId);
            model.addAttribute("userId", userId);
            return processShowRegisterPage(model, courseId);
        }

        //         if already register

        // get all topic to display the side bar
        List<TopicResponse> topicResponses = courseService
                .findAllTopicsResponseWithJoinFetch(courseId, userId);

        // check does course have any lesson
        if (topicResponses.isEmpty()) {
            System.out.println("Course Empty");
            model.addAttribute("message", "Không tìm thấy bài học!!");
            return "/User/Inform";
        }

        model.addAttribute("message", "");
        model.addAttribute("topicResponse",topicResponses);

        // set api to show lesson
        String getPostApi = api + "/post/show_post";
        model.addAttribute("api",getPostApi);

        System.out.println("Topic Response: " + topicResponses);
        // get first post from lesson get from topic
        int postId = topicResponses.get(0).getPostResponses().get(0).getId();

        PostResponse postResponse = courseService.findPostById(postId);


        // load comment for post
//        List<CommentResponse> commentResponses = postService.findAllCommentsByPostId(postId);
//        if (commentResponses == null) logger.info("Comment is null");
//
//        System.out.println("Comment Id: " +commentResponses.get(0).getId());
//        System.out.println("Content: " + commentResponses.get(0).getContent());
//
//
//        model.addAttribute("commentResponses", commentResponses);

        // show post by add post response to model
        model.addAttribute("postResponse", postResponse);

        // get first post
        if (topicResponses != null) {

            // get first post id from the topic response
//            int firstPostId = topicResponses.getFirst().getPostResponses().getFirst().getId();

        }

        return "/User/post-page";

    }

    private String processShowRegisterPage(Model model, int courseId) {
        // get course to show for register course
        CourseResponse courseResponse = courseService.findCourseById(courseId);

        logger.info("my course: " + courseResponse );
        model.addAttribute("courseResponse", courseResponse);

        logger.info("Vào đăng kí");
        return "/User/registercourse";
    }

    @GetMapping("/process_register_course")
    public String registerCourse(
        @RequestParam("course_id") int courseId,
        @RequestParam("user_id") Integer userId,
        Model model
    ) {

        logger.info("User id: " + userId);
        // USER HAVE TO LOAD FROM AUTHEN


        CourseRegistration courseRegistration = courseService.findUserCourseRegistration(userId, courseId);
        // handle if user not register course
        if (courseRegistration == null) {
            int result = courseService.registerCourse(userId, courseId);

            // result > mean course free
            if (result > 0) {
                model.addAttribute("courseId", courseId);
                model.addAttribute("userId", userId);
                return "/User/RegisterCourseSuccessfully";
            }

            // move to this page iff the course not free
            return "/User/CoursePaymentPage";
        }


        // if already register but not pay course
        return "/User/CoursePaymentPage";

    }


}

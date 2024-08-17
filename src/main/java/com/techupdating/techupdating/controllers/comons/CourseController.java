package com.techupdating.techupdating.controllers.comons;

import com.techupdating.techupdating.Services.CourseService;
import com.techupdating.techupdating.models.CourseRegistration;
import com.techupdating.techupdating.responses.CourseResponse;
import lombok.RequiredArgsConstructor;
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
    public String getCourses (
        @PathVariable("id") int id,
        Model model
    ) {

        List<CourseResponse> courses = courseService.findCoursesByLanguageId(id);

        model.addAttribute("courses", courses);

        return "/User/course-page";
    }

    @GetMapping("/process_show_lesson")
    public String processShowLesson(
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

        return "/User/lesson";

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

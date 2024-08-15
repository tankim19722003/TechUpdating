package com.techupdating.techupdating.controllers.comons;

import com.techupdating.techupdating.Services.CourseService;
import com.techupdating.techupdating.responses.CourseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/v1/dev_updating/course")
@RequiredArgsConstructor
public class CourseController {

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

    @GetMapping("/process_show_lesson/{id}")
    public String processShowLesson(
            @PathVariable("id") int id,
            Model model
    ) {

        CourseResponse courseResponse = courseService.findCourseById(id);
        model.addAttribute("courseResponse", courseResponse);
        //         if already register
        return "/User/registercourse.html";


        //    if does not register
//        return "/User/registerCourse"
    }
}

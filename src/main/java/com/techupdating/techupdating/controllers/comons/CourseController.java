package com.techupdating.techupdating.controllers.comons;

import com.techupdating.techupdating.Services.CourseService;
import com.techupdating.techupdating.responses.CourseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.awt.print.Pageable;
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
}

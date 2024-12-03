package com.techupdating.techupdating.controllers.commons;

import com.techupdating.techupdating.Services.LanguageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/dev_updating/language")
@RequiredArgsConstructor
public class LanguageController {

    private final LanguageService languageService;

    @GetMapping("/get_all_languages")
    public ResponseEntity<?> getAllLanguages() {

        return ResponseEntity.ok(languageService.findAllLanguages());
    }

}

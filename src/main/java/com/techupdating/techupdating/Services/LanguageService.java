package com.techupdating.techupdating.Services;

import com.techupdating.techupdating.models.Language;
import com.techupdating.techupdating.responses.LanguageResponse;

import java.util.List;

public interface LanguageService {
    List<LanguageResponse> findAllLanguages();
}

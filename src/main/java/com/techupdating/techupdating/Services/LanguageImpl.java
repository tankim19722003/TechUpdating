package com.techupdating.techupdating.Services;

import com.techupdating.techupdating.models.Language;
import com.techupdating.techupdating.repositories.LanguageRepository;
import com.techupdating.techupdating.responses.LanguageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LanguageImpl implements  LanguageService{

    private final LanguageRepository languageRepository;


    @Override
    public List<LanguageResponse> findAllLanguages() {
        List<LanguageResponse>languages =  languageRepository.findAll().stream().map(
                language -> {
                    return LanguageResponse.builder()
                            .id(language.getId())
                            .name(language.getName())
                            .build();
                }).toList();

        return languages;
    }
}

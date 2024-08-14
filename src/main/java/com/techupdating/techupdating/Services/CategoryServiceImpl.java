package com.techupdating.techupdating.Services;

import com.techupdating.techupdating.models.Category;
import com.techupdating.techupdating.repositories.CategoryRepository;
import com.techupdating.techupdating.responses.CategoryResponse;
import com.techupdating.techupdating.responses.LanguageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;


    @Override
    public CategoryResponse findCategoryById(int categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new RuntimeException("Category does not exist")
        );

        CategoryResponse categoryResponse = new CategoryResponse();

        categoryResponse.setId(category.getId());
        categoryResponse.setName(category.getName());
        categoryResponse.setLanguageResponses(category.getLanguages().stream().map(
                language -> LanguageResponse.builder()
                            .id(language.getId())
                            .name(language.getName())
                            .imageName(language.getImageName())
                            .build()).toList()
        );

        return categoryResponse;

    }
}

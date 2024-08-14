package com.techupdating.techupdating.Services;


import com.techupdating.techupdating.models.Category;
import com.techupdating.techupdating.responses.CategoryResponse;

public interface CategoryService {
    CategoryResponse findCategoryById(int categoryId);
}

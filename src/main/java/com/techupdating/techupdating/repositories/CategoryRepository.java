package com.techupdating.techupdating.repositories;

import com.techupdating.techupdating.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}

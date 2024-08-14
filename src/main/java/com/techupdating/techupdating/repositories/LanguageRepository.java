package com.techupdating.techupdating.repositories;

import com.techupdating.techupdating.models.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

public interface LanguageRepository extends JpaRepository<Language, Integer> {

    // find language with join point
    @Query("select l from Language l join fetch l.courses where l.id = :id")
    Optional<Language> findLanguageWithJoinFetch(@RequestParam("id") int id) ;

}

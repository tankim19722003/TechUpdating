package com.techupdating.techupdating.Services;

import com.techupdating.techupdating.dtos.PostDTO;
import com.techupdating.techupdating.models.Language;
import com.techupdating.techupdating.models.Post;

import java.io.IOException;
import java.util.List;

public interface PostService {

    Post createPost (PostDTO postDTO) throws IOException;

    List<Language> findAllLanguage();
}

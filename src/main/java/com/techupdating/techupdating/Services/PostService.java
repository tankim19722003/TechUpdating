package com.techupdating.techupdating.Services;

import com.techupdating.techupdating.dtos.PostDTO;
import com.techupdating.techupdating.models.Post;

import java.io.IOException;

public interface PostService {

    Post createPost (PostDTO postDTO) throws IOException;
}

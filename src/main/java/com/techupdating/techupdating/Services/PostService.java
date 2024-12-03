package com.techupdating.techupdating.Services;

import com.techupdating.techupdating.dtos.PostCreatingDTO;
import com.techupdating.techupdating.dtos.PostDTO;
import com.techupdating.techupdating.models.CourseRegistration;
import com.techupdating.techupdating.models.Language;
import com.techupdating.techupdating.models.Post;
import com.techupdating.techupdating.responses.CommentResponse;
import com.techupdating.techupdating.responses.PostResponse;
import com.techupdating.techupdating.responses.PostSelectResponse;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface PostService {

    PostResponse createPost (int userId, PostCreatingDTO postCreatingDTO) throws IOException;

    List<Language> findAllLanguage();

    CourseRegistration findAllPosts(int userId, int courseId);

    PostResponse findPostById(int postId);

     List<CommentResponse> findAllCommentsByPostId(int postId);

    List<PostSelectResponse> getIdAndTitlePostByCourseId(int courseId);
}

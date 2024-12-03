package com.techupdating.techupdating.Services;

import com.techupdating.techupdating.responses.TopicResponse;

import java.util.List;

public interface PostTopicService {
    List<TopicResponse> getTopicsResponseByCourseId(int courseId);
    List<TopicResponse> findPostGroups(int courseId);
}

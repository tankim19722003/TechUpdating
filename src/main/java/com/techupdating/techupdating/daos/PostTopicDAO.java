package com.techupdating.techupdating.daos;

import com.techupdating.techupdating.responses.TopicResponse;

import java.util.List;

public interface PostTopicDAO {

    List<TopicResponse> findAllTopicWithJoinFetch(
            int userId,
            int courseId
    ) ;
}

package com.techupdating.techupdating.preHandle;

import com.techupdating.techupdating.daos.PostTopicDAO;
import com.techupdating.techupdating.responses.TopicResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.List;

@Component
public class PreTopic {

    private static PostTopicDAO postTopicDAO;

    @Autowired
    public PreTopic(PostTopicDAO postTopicDAO) {
        this.postTopicDAO = postTopicDAO;
    }

    public static List<TopicResponse> getListTopicsResponse(Model model, int userId, int courseId) {
       return postTopicDAO.findAllTopicWithJoinFetch(userId, courseId);
    }

}

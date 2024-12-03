package com.techupdating.techupdating.daos;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PostDAO {
    private EntityManager entityManager;

    @Autowired
    public PostDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Object[]> findPostIdAndTitleByTopicId(int topicId) {
        String jpql = "SELECT p.id, p.title FROM Post p where p.postTopic.id = :topicId";
        return entityManager.createQuery(jpql, Object[].class)
                .setParameter("topicId", topicId)
                .getResultList();
    }
}

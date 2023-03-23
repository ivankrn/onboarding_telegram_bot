package com.ppteam.onboardingtelegrambot.database;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository extends PagingAndSortingRepository<Test, Long> {
    Page<Test> findByTopicId(int topicId, Pageable page);
//    @EntityGraph(
//            value = "test-entity-graph-with-questions-answers",
//            type = EntityGraph.EntityGraphType.LOAD
//    )
    @EntityGraph(attributePaths = {"questions", "questions.answers"})
    Test findById(int id);
}

package com.ppteam.onboardingtelegrambot.database;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TestRepository extends PagingAndSortingRepository<Test, Long>, CrudRepository<Test, Long> {
    Page<Test> findByTopicId(int topicId, Pageable page);
//    @EntityGraph(
//            value = "test-entity-graph-with-questions-answers",
//            type = EntityGraph.EntityGraphType.LOAD
//    )

    @EntityGraph(attributePaths = {"questions", "questions.answers"})
    Optional<Test> findById(long id);
}

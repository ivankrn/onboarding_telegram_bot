package com.ppteam.onboardingtelegrambot.database;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestSessionRepository extends CrudRepository<TestSession, Long> {
    @EntityGraph(attributePaths = {"passedQuestions"})
    TestSession findByUserId(long userId);
}

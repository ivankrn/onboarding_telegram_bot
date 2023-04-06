package com.ppteam.onboardingtelegrambot.database;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestQuestionRepository extends CrudRepository<TestQuestion, Long> {
    @EntityGraph(attributePaths = {"answers"})
    List<TestQuestion> findByTestId(long testId);
}

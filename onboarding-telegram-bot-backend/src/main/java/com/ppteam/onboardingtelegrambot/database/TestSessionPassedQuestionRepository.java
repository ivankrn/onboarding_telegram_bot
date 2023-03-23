package com.ppteam.onboardingtelegrambot.database;

import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TestSessionPassedQuestionRepository extends CrudRepository<TestSessionPassedQuestion, Long> {
    List<TestQuestion> findByTestSessionId(int testId);
    @Transactional
    Long deleteByTestSessionId(int testSessionId);
}

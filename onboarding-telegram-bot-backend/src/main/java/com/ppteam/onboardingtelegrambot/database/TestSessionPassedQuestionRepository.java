package com.ppteam.onboardingtelegrambot.database;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TestSessionPassedQuestionRepository extends CrudRepository<TestSessionPassedQuestion, Long> {
    List<TestSessionPassedQuestion> findByTestSessionUserId(long userId);
}

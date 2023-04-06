package com.ppteam.onboardingtelegrambot.service;

import com.ppteam.onboardingtelegrambot.database.TestQuestion;

import java.util.List;

public interface TestQuestionService {
    TestQuestion findById(long id);
    List<TestQuestion> findByTestId(long testId);
    boolean exists(long id);
}

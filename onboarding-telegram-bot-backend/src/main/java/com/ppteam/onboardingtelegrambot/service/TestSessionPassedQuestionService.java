package com.ppteam.onboardingtelegrambot.service;

import com.ppteam.onboardingtelegrambot.dto.TestSessionPassedQuestionDto;

import java.util.List;

public interface TestSessionPassedQuestionService {
    List<TestSessionPassedQuestionDto> findByUserId(long sessionId);
    void save(TestSessionPassedQuestionDto passedQuestion);
}

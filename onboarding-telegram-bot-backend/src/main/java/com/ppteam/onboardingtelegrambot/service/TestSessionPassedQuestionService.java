package com.ppteam.onboardingtelegrambot.service;

import com.ppteam.onboardingtelegrambot.dto.TestSessionPassedQuestionDto;

import java.util.List;

public interface TestSessionPassedQuestionService {
    List<TestSessionPassedQuestionDto> findBySessionId(long sessionId);
    void save(TestSessionPassedQuestionDto passedQuestion);
}

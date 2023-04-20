package com.ppteam.onboardingtelegrambot.service;

import com.ppteam.onboardingtelegrambot.dto.TestSessionDto;

public interface TestSessionService {
    TestSessionDto findByUserId(long userId);
    boolean hasActiveTestSession(long userId);
    void createForUserAndTest(long userId, long testId);
    void save(TestSessionDto session);
    void increaseScore(long userId);
    void deleteByUserId(long userId);
}

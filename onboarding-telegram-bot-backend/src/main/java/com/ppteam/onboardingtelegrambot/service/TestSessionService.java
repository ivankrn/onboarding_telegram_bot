package com.ppteam.onboardingtelegrambot.service;

import com.ppteam.onboardingtelegrambot.database.TestSession;

public interface TestSessionService {
    TestSession findByUserId(long userId);
    boolean hasActiveTestSession(long userId);
    void save(TestSession session);
    void increaseScore(TestSession session);
    void delete(TestSession session);
}

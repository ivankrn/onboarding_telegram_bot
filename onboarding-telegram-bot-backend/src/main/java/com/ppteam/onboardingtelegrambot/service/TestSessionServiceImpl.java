package com.ppteam.onboardingtelegrambot.service;

import com.ppteam.onboardingtelegrambot.controller.error.NotFoundException;
import com.ppteam.onboardingtelegrambot.database.TestSession;
import com.ppteam.onboardingtelegrambot.database.TestSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestSessionServiceImpl implements TestSessionService {
    private final TestSessionRepository testSessionRepository;

    @Override
    public TestSession findByUserId(long userId) {
        return testSessionRepository.findByUserId(userId).orElseThrow(NotFoundException::new);
    }

    @Override
    public boolean hasActiveTestSession(long userId) {
        return testSessionRepository.findByUserId(userId).isPresent();
    }

    @Override
    public void save(TestSession session) {
        testSessionRepository.save(session);
    }

    @Override
    public void increaseScore(TestSession session) {
        session.setScore(session.getScore() + 1);
        testSessionRepository.save(session);
    }

    @Override
    public void delete(TestSession session) {
        testSessionRepository.delete(session);
    }
}

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
        return this.testSessionRepository.findByUserId(userId).orElseThrow(NotFoundException::new);
    }

    @Override
    public boolean hasActiveTestSession(long userId) {
        return this.testSessionRepository.findByUserId(userId).isPresent();
    }

    @Override
    public void save(TestSession session) {
        this.testSessionRepository.save(session);
    }

    @Override
    public void increaseScore(TestSession session) {
        session.setScore(session.getScore() + 1);
        this.testSessionRepository.save(session);
    }

    @Override
    public void delete(TestSession session) {
        this.testSessionRepository.delete(session);
    }
}

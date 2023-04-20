package com.ppteam.onboardingtelegrambot.service;

import com.ppteam.onboardingtelegrambot.controller.error.NotFoundException;
import com.ppteam.onboardingtelegrambot.database.TestSession;
import com.ppteam.onboardingtelegrambot.database.TestSessionRepository;
import com.ppteam.onboardingtelegrambot.dto.TestSessionDto;
import com.ppteam.onboardingtelegrambot.dto.mappers.MapStructMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestSessionServiceImpl implements TestSessionService {
    private final TestSessionRepository testSessionRepository;
    private final MapStructMapper mapStructMapper;

    @Override
    public TestSessionDto findByUserId(long userId) {
        return testSessionRepository.findByUserId(userId).map(mapStructMapper::testSessionToTestSessionDto)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public boolean hasActiveTestSession(long userId) {
        return testSessionRepository.findByUserId(userId).isPresent();
    }

    @Override
    public void createForUserAndTest(long userId, long testId) {
        if (!testSessionRepository.existsById(userId)) {
            TestSession session = new TestSession();
            session.setUserId(userId);
            session.setTestId(testId);
            testSessionRepository.save(session);
        }
    }

    @Override
    public void save(TestSessionDto session) {
        testSessionRepository.save(mapStructMapper.testSessionDtoToTestSession(session));
    }

    @Override
    @Transactional
    public void increaseScore(long userId) {
        testSessionRepository.increaseScore(userId);
    }

    @Override
    public void deleteByUserId(long userId) {
        testSessionRepository.deleteById(userId);
    }
}

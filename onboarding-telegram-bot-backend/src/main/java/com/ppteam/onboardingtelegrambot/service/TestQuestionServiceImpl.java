package com.ppteam.onboardingtelegrambot.service;

import com.ppteam.onboardingtelegrambot.controller.error.NotFoundException;
import com.ppteam.onboardingtelegrambot.database.TestQuestion;
import com.ppteam.onboardingtelegrambot.database.TestQuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestQuestionServiceImpl implements TestQuestionService {
    private final TestQuestionRepository testQuestionRepository;

    @Override
    public TestQuestion findById(long id) {
        return this.testQuestionRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public List<TestQuestion> findByTestId(long testId) {
        return this.testQuestionRepository.findByTestId(testId);
    }

    @Override
    public boolean exists(long id) {
        return this.testQuestionRepository.existsById(id);
    }
}

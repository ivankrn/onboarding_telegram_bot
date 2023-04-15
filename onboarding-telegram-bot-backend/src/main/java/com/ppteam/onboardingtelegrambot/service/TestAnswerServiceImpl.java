package com.ppteam.onboardingtelegrambot.service;

import com.ppteam.onboardingtelegrambot.controller.error.NotFoundException;
import com.ppteam.onboardingtelegrambot.database.TestAnswer;
import com.ppteam.onboardingtelegrambot.database.TestAnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestAnswerServiceImpl implements TestAnswerService {
    private final TestAnswerRepository testAnswerRepository;

    @Override
    public TestAnswer getCorrectAnswerForQuestionId(long questionId) {
        return this.testAnswerRepository.findCorrectAnswerForQuestionId(questionId).orElseThrow(NotFoundException::new);
    }
}

package com.ppteam.onboardingtelegrambot.service;

import com.ppteam.onboardingtelegrambot.dto.TestQuestionDto;
import com.ppteam.onboardingtelegrambot.dto.TestQuestionFullDto;

import java.util.List;

public interface TestQuestionService {
    TestQuestionDto findById(long id);
    TestQuestionFullDto findByIdWithAnswers(long id);
    List<TestQuestionDto> findByTestId(long testId);
    boolean exists(long id);
}

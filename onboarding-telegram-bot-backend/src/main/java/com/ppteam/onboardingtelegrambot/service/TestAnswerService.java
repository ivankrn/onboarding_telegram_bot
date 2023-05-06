package com.ppteam.onboardingtelegrambot.service;

import com.ppteam.onboardingtelegrambot.dto.TestAnswerDto;

import java.util.Set;

public interface TestAnswerService {
    TestAnswerDto getCorrectAnswerForQuestionId(long questionId);
    Set<TestAnswerDto> getCorrectAnswersForQuestionId(long questionId);
}

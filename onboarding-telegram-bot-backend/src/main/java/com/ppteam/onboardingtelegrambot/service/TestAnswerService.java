package com.ppteam.onboardingtelegrambot.service;

import com.ppteam.onboardingtelegrambot.dto.TestAnswerDto;

public interface TestAnswerService {
    TestAnswerDto getCorrectAnswerForQuestionId(long questionId);
}

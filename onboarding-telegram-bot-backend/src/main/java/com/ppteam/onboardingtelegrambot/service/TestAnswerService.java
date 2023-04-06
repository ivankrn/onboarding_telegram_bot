package com.ppteam.onboardingtelegrambot.service;

import com.ppteam.onboardingtelegrambot.database.TestAnswer;

public interface TestAnswerService {
    TestAnswer getCorrectAnswerForQuestionId(long questionId);
}

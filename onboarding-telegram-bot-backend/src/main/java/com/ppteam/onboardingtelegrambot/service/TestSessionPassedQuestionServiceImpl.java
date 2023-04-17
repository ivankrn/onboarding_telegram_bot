package com.ppteam.onboardingtelegrambot.service;

import com.ppteam.onboardingtelegrambot.database.TestSessionPassedQuestion;
import com.ppteam.onboardingtelegrambot.database.TestSessionPassedQuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestSessionPassedQuestionServiceImpl implements TestSessionPassedQuestionService {
    private final TestSessionPassedQuestionRepository testSessionPassedQuestionRepository;

    @Override
    public void save(TestSessionPassedQuestion passedQuestion) {
        testSessionPassedQuestionRepository.save(passedQuestion);
    }
}

package com.ppteam.onboardingtelegrambot.service;

import com.ppteam.onboardingtelegrambot.database.TestSessionPassedQuestionRepository;
import com.ppteam.onboardingtelegrambot.dto.TestSessionPassedQuestionDto;
import com.ppteam.onboardingtelegrambot.dto.mappers.TestSessionPassedQuestionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TestSessionPassedQuestionServiceImpl implements TestSessionPassedQuestionService {
    private final TestSessionPassedQuestionRepository testSessionPassedQuestionRepository;
    private final TestSessionPassedQuestionMapper testSessionPassedQuestionMapper;

    @Override
    public List<TestSessionPassedQuestionDto> findByUserId(long userId) {
        return testSessionPassedQuestionRepository.findByTestSessionUserId(userId).stream()
                .map(testSessionPassedQuestionMapper::passedQuestionToPassedQuestionDto).collect(Collectors.toList());
    }

    @Override
    public void save(TestSessionPassedQuestionDto passedQuestion) {
        testSessionPassedQuestionRepository.save(testSessionPassedQuestionMapper.passedQuestionDtoToPassedQuestion(passedQuestion));
    }
}

package com.ppteam.onboardingtelegrambot.service;

import com.ppteam.onboardingtelegrambot.controller.error.NotFoundException;
import com.ppteam.onboardingtelegrambot.database.TestAnswerRepository;
import com.ppteam.onboardingtelegrambot.dto.TestAnswerDto;
import com.ppteam.onboardingtelegrambot.dto.mappers.TestAnswerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TestAnswerServiceImpl implements TestAnswerService {
    private final TestAnswerRepository testAnswerRepository;
    private final TestAnswerMapper testAnswerMapper;

    @Override
    public TestAnswerDto getCorrectAnswerForQuestionId(long questionId) {
        return testAnswerRepository.findCorrectAnswerForQuestionId(questionId)
                .map(testAnswerMapper::testAnswerToTestAnswerDto).orElseThrow(NotFoundException::new);
    }

    @Override
    public Set<TestAnswerDto> getCorrectAnswersForQuestionId(long questionId) {
        return testAnswerRepository.findCorrectAnswersForQuestionId(questionId)
                .map(testAnswers ->
                        testAnswers.stream().map(testAnswerMapper::testAnswerToTestAnswerDto).collect(Collectors.toSet())
                ).orElseThrow(NotFoundException::new);
    }
}

package com.ppteam.onboardingtelegrambot.service;

import com.ppteam.onboardingtelegrambot.controller.error.NotFoundException;
import com.ppteam.onboardingtelegrambot.database.TestAnswerRepository;
import com.ppteam.onboardingtelegrambot.dto.TestAnswerDto;
import com.ppteam.onboardingtelegrambot.dto.mappers.MapStructMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TestAnswerServiceImpl implements TestAnswerService {
    private final TestAnswerRepository testAnswerRepository;
    private final MapStructMapper mapStructMapper;

    @Override
    public TestAnswerDto getCorrectAnswerForQuestionId(long questionId) {
        return testAnswerRepository.findCorrectAnswerForQuestionId(questionId)
                .map(mapStructMapper::testAnswerToTestAnswerDto).orElseThrow(NotFoundException::new);
    }

    @Override
    public Set<TestAnswerDto> getCorrectAnswersForQuestionId(long questionId) {
        return testAnswerRepository.findCorrectAnswersForQuestionId(questionId)
                .map(testAnswers ->
                        testAnswers.stream().map(mapStructMapper::testAnswerToTestAnswerDto).collect(Collectors.toSet())
                ).orElseThrow(NotFoundException::new);
    }
}

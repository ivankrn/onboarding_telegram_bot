package com.ppteam.onboardingtelegrambot.service;

import com.ppteam.onboardingtelegrambot.controller.error.NotFoundException;
import com.ppteam.onboardingtelegrambot.database.TestQuestionRepository;
import com.ppteam.onboardingtelegrambot.dto.TestQuestionDto;
import com.ppteam.onboardingtelegrambot.dto.TestQuestionFullDto;
import com.ppteam.onboardingtelegrambot.dto.mappers.TestQuestionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TestQuestionServiceImpl implements TestQuestionService {
    private final TestQuestionRepository testQuestionRepository;
    private final TestQuestionMapper testQuestionMapper;

    @Override
    public TestQuestionDto findById(long id) {
        return testQuestionRepository.findById(id).map(testQuestionMapper::testQuestionToTestQuestionDto)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public TestQuestionFullDto findByIdWithAnswers(long id) {
        return testQuestionRepository.findByIdWithAnswers(id).map(testQuestionMapper::testQuestionToTestQuestionFullDto)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public List<TestQuestionDto> findByTestId(long testId) {
        return testQuestionRepository.findByTestId(testId).stream().map(testQuestionMapper::testQuestionToTestQuestionDto)
                .collect(Collectors.toList());
    }

    @Override
    public boolean exists(long id) {
        return testQuestionRepository.existsById(id);
    }
}

package com.ppteam.onboardingtelegrambot.service;

import com.ppteam.onboardingtelegrambot.database.TestSessionPassedQuestionRepository;
import com.ppteam.onboardingtelegrambot.dto.TestSessionPassedQuestionDto;
import com.ppteam.onboardingtelegrambot.dto.mappers.MapStructMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TestSessionPassedQuestionServiceImpl implements TestSessionPassedQuestionService {
    private final TestSessionPassedQuestionRepository testSessionPassedQuestionRepository;
    private final MapStructMapper mapStructMapper;

    @Override
    public List<TestSessionPassedQuestionDto> findBySessionId(long sessionId) {
        return testSessionPassedQuestionRepository.findByTestSessionId(sessionId).stream()
                .map(mapStructMapper::passedQuestionToPassedQuestionDto).collect(Collectors.toList());
    }

    @Override
    public void save(TestSessionPassedQuestionDto passedQuestion) {
        testSessionPassedQuestionRepository.save(mapStructMapper.passedQuestionDtoToPassedQuestion(passedQuestion));
    }
}

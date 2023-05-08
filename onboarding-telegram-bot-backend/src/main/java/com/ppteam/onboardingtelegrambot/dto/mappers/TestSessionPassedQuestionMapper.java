package com.ppteam.onboardingtelegrambot.dto.mappers;

import com.ppteam.onboardingtelegrambot.database.TestSessionPassedQuestion;
import com.ppteam.onboardingtelegrambot.dto.TestSessionPassedQuestionDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = TestSessionMapper.class)
public interface TestSessionPassedQuestionMapper {
    TestSessionPassedQuestionDto passedQuestionToPassedQuestionDto(TestSessionPassedQuestion passedQuestion);

    TestSessionPassedQuestion passedQuestionDtoToPassedQuestion(TestSessionPassedQuestionDto passedQuestionDto);
}

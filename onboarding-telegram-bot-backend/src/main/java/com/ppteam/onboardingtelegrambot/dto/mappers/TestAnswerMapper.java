package com.ppteam.onboardingtelegrambot.dto.mappers;

import com.ppteam.onboardingtelegrambot.database.TestAnswer;
import com.ppteam.onboardingtelegrambot.dto.TestAnswerDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TestAnswerMapper {
    TestAnswerDto testAnswerToTestAnswerDto(TestAnswer testAnswer);

    TestAnswer testAnswerDtoToTestAnswer(TestAnswerDto testAnswerDto);
}

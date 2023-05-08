package com.ppteam.onboardingtelegrambot.dto.mappers;

import com.ppteam.onboardingtelegrambot.database.TestQuestion;
import com.ppteam.onboardingtelegrambot.dto.TestQuestionDto;
import com.ppteam.onboardingtelegrambot.dto.TestQuestionFullDto;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = TestAnswerMapper.class, collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface TestQuestionMapper {
    TestQuestionFullDto testQuestionToTestQuestionFullDto(TestQuestion testQuestion);

    TestQuestion testQuestionFullDtoToTestQuestion(TestQuestionFullDto testQuestionFullDto);

    TestQuestionDto testQuestionToTestQuestionDto(TestQuestion testQuestion);
}

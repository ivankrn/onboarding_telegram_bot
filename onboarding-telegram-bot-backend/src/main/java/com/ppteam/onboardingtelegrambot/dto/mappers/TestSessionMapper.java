package com.ppteam.onboardingtelegrambot.dto.mappers;

import com.ppteam.onboardingtelegrambot.database.TestSession;
import com.ppteam.onboardingtelegrambot.dto.TestSessionDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TestSessionMapper {
    TestSessionDto testSessionToTestSessionDto(TestSession testSession);
    TestSession testSessionDtoToTestSession(TestSessionDto testSessionDto);
}

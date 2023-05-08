package com.ppteam.onboardingtelegrambot.dto.mappers;

import com.ppteam.onboardingtelegrambot.database.TestStatistic;
import com.ppteam.onboardingtelegrambot.dto.TestStatisticDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TestStatisticMapper {
    @Mapping(source = "testStatistic.test.title", target = "testTitle")
    TestStatisticDto testStatisticToTestStatisticDto(TestStatistic testStatistic);

    TestStatistic testStatisticDtoToTestStatistic(TestStatisticDto testStatisticDto);
}

package com.ppteam.onboardingtelegrambot.dto.mappers;

import com.ppteam.onboardingtelegrambot.database.*;
import com.ppteam.onboardingtelegrambot.dto.*;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring", collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface MapStructMapper {
    TestDto testToTestDto(Test test);
    Test testDtoToTest(TestDto testDto);
    TestFullDto testToTestFullDto(Test test);
    Test testFullDtoToTest(TestFullDto testFullDto);
    TestQuestionFullDto testQuestionToTestQuestionFullDto(TestQuestion testQuestion);
    TestQuestion testQuestionFullDtoToTestQuestion(TestQuestionFullDto testQuestionFullDto);
    TestQuestionDto testQuestionToTestQuestionDto(TestQuestion testQuestion);
    TestAnswerDto testAnswerToTestAnswerDto(TestAnswer testAnswer);
    TestAnswer testAnswerDtoToTestAnswer(TestAnswerDto testAnswerDto);
    ArticleDto articleToArticleDto(Article article);
    Article articleDtoToArticle(ArticleDto articleDto);
    ArticleTopicDto topicToTopicDto(ArticleTopic articleTopic);

    TestStatisticDto testStatisticToTestStatisticDto(TestStatistic testStatistic);
    TestStatistic testStatisticDtoToTestStatistic(TestStatisticDto testStatisticDto);
    ArticleStatisticDto articleStatisticToArticleStatisticDto(ArticleStatistic articleStatistic);
    ArticleStatistic articleStatisticDtoToArticleStatistic(ArticleStatisticDto articleStatisticDto);
    TestSessionDto testSessionToTestSessionDto(TestSession testSession);
    TestSession testSessionDtoToTestSession(TestSessionDto testSessionDto);
    TestSessionPassedQuestionDto passedQuestionToPassedQuestionDto(TestSessionPassedQuestion passedQuestion);
    TestSessionPassedQuestion passedQuestionDtoToPassedQuestion(TestSessionPassedQuestionDto passedQuestionDto);
}

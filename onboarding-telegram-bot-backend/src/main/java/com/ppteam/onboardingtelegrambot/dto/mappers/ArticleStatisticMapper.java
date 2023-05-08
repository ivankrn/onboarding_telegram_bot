package com.ppteam.onboardingtelegrambot.dto.mappers;

import com.ppteam.onboardingtelegrambot.database.ArticleStatistic;
import com.ppteam.onboardingtelegrambot.dto.ArticleStatisticDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ArticleStatisticMapper {
    @Mapping(source = "articleStatistic.article.title", target = "articleTitle")
    ArticleStatisticDto articleStatisticToArticleStatisticDto(ArticleStatistic articleStatistic);
    ArticleStatistic articleStatisticDtoToArticleStatistic(ArticleStatisticDto articleStatisticDto);
}

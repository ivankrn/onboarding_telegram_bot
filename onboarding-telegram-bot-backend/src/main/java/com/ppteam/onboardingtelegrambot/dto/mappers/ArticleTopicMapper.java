package com.ppteam.onboardingtelegrambot.dto.mappers;

import com.ppteam.onboardingtelegrambot.database.ArticleTopic;
import com.ppteam.onboardingtelegrambot.dto.ArticleTopicDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ArticleTopicMapper {
    ArticleTopicDto topicToTopicDto(ArticleTopic articleTopic);

    ArticleTopic topicDtoToTopic(ArticleTopicDto articleTopicDto);
}

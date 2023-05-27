package com.ppteam.onboardingtelegrambot.dto.mappers;

import com.ppteam.onboardingtelegrambot.database.ArticleTopic;
import com.ppteam.onboardingtelegrambot.dto.ArticleTopicCreateDto;
import com.ppteam.onboardingtelegrambot.dto.ArticleTopicDto;
import com.ppteam.onboardingtelegrambot.dto.ArticleTopicPatchDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ArticleTopicMapper {
    ArticleTopicDto topicToTopicDto(ArticleTopic articleTopic);
    ArticleTopic topicDtoToTopic(ArticleTopicDto articleTopicDto);
    ArticleTopic topicCreateDtoToTopic(ArticleTopicCreateDto articleTopicCreateDto);
    default void patchArticleTopicFromDto(ArticleTopicPatchDto articleTopicPatchDto, @MappingTarget ArticleTopic topic) {
        if (articleTopicPatchDto == null) {
            return;
        }
        if (articleTopicPatchDto.getName() != null) {
            topic.setName(articleTopicPatchDto.getName());
        }
    }
}

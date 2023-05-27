package com.ppteam.onboardingtelegrambot.service;

import com.ppteam.onboardingtelegrambot.dto.ArticleTopicCreateDto;
import com.ppteam.onboardingtelegrambot.dto.ArticleTopicDto;
import com.ppteam.onboardingtelegrambot.dto.ArticleTopicPatchDto;

import java.util.List;

public interface ArticleTopicService {
    List<ArticleTopicDto> findAll();
    void create(ArticleTopicCreateDto topic);
    void updatePartial(long id, ArticleTopicPatchDto topicPatchDto);
    void deleteById(long id);
}

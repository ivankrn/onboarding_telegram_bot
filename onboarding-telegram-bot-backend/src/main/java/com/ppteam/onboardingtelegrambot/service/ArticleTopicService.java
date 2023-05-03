package com.ppteam.onboardingtelegrambot.service;

import com.ppteam.onboardingtelegrambot.dto.ArticleTopicDto;

import java.util.List;

public interface ArticleTopicService {
    List<ArticleTopicDto> findAll();
    void save(ArticleTopicDto topic);
    void deleteById(long id);
}

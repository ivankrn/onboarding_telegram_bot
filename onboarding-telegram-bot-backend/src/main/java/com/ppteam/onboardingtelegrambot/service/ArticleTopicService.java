package com.ppteam.onboardingtelegrambot.service;

import com.ppteam.onboardingtelegrambot.database.ArticleTopic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArticleTopicService {
    Page<ArticleTopic> findAll(Pageable page);
}

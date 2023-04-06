package com.ppteam.onboardingtelegrambot.service;

import com.ppteam.onboardingtelegrambot.database.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArticleService {
    Page<Article> findAll(Pageable page);

    Article findById(long id);

    Page<Article> findByTopicId(long topicId, Pageable page);

    long count();

    void save(Article article);

    void deleteById(long id);
}

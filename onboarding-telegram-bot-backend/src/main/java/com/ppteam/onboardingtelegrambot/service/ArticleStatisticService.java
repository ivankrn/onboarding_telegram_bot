package com.ppteam.onboardingtelegrambot.service;

import com.ppteam.onboardingtelegrambot.database.ArticleStatistic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArticleStatisticService {
    Page<ArticleStatistic> findAll(Pageable page);

    ArticleStatistic findByArticleId(long articleId);

    ArticleStatistic findById(long id);

    void save(ArticleStatistic articleStatistic);
    boolean existsByArticleId(long articleId);
    void deleteById(long id);
}

package com.ppteam.onboardingtelegrambot.service;

import com.ppteam.onboardingtelegrambot.dto.ArticleStatisticDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArticleStatisticService {
    Page<ArticleStatisticDto> findAll(Pageable page);

    ArticleStatisticDto findById(long id);

    void save(ArticleStatisticDto articleStatistic);

    void deleteById(long id);

    void updateForArticle(long articleId, int rating);
}

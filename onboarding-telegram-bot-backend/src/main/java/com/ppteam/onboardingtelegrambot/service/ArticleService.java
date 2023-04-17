package com.ppteam.onboardingtelegrambot.service;

import com.ppteam.onboardingtelegrambot.dto.ArticleDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArticleService {
    Page<ArticleDto> findAll(Pageable page);

    ArticleDto findById(long id);

    Page<ArticleDto> findByTopicId(long topicId, Pageable page);

    long count();

    void save(ArticleDto articleDto);

    void deleteById(long id);
}

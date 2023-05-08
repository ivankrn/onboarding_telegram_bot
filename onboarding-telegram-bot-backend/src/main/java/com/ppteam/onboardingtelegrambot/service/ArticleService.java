package com.ppteam.onboardingtelegrambot.service;

import com.ppteam.onboardingtelegrambot.dto.ArticleCreateDto;
import com.ppteam.onboardingtelegrambot.dto.ArticleDto;
import com.ppteam.onboardingtelegrambot.dto.ArticleUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArticleService {
    Page<ArticleDto> findAll(Pageable page);

    ArticleDto findById(long id);

    Page<ArticleDto> findByTopicId(long topicId, Pageable page);

    long count();

    void create(ArticleCreateDto articleCreateDto);
    void update(long id, ArticleUpdateDto articleUpdateDto);

    void deleteById(long id);
}

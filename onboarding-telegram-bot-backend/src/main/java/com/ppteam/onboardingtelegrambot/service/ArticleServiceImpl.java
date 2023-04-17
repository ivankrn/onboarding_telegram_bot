package com.ppteam.onboardingtelegrambot.service;

import com.ppteam.onboardingtelegrambot.controller.error.NotFoundException;
import com.ppteam.onboardingtelegrambot.database.ArticleRepository;
import com.ppteam.onboardingtelegrambot.dto.ArticleDto;
import com.ppteam.onboardingtelegrambot.dto.mappers.MapStructMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;
    private final MapStructMapper mapStructMapper;

    @Override
    public Page<ArticleDto> findAll(Pageable page) {
        return articleRepository.findAll(page).map(mapStructMapper::articleToArticleDto);
    }

    @Override
    public ArticleDto findById(long id) {
        return articleRepository.findById(id).map(mapStructMapper::articleToArticleDto)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public Page<ArticleDto> findByTopicId(long topicId, Pageable page) {
        return articleRepository.findByTopicId(topicId, page).map(mapStructMapper::articleToArticleDto);
    }

    @Override
    public long count() {
        return articleRepository.count();
    }

    @Override
    public void save(ArticleDto article) {
        articleRepository.save(mapStructMapper.articleDtoToArticle(article));
    }

    @Override
    public void deleteById(long id) {
        articleRepository.deleteById(id);
    }
}

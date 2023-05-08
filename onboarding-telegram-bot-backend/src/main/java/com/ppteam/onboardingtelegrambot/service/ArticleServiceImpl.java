package com.ppteam.onboardingtelegrambot.service;

import com.ppteam.onboardingtelegrambot.controller.error.NotFoundException;
import com.ppteam.onboardingtelegrambot.database.Article;
import com.ppteam.onboardingtelegrambot.database.ArticleRepository;
import com.ppteam.onboardingtelegrambot.dto.ArticleCreateDto;
import com.ppteam.onboardingtelegrambot.dto.ArticleDto;
import com.ppteam.onboardingtelegrambot.dto.ArticleUpdateDto;
import com.ppteam.onboardingtelegrambot.dto.mappers.ArticleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;

    @Override
    public Page<ArticleDto> findAll(Pageable page) {
        return articleRepository.findAll(page).map(article -> articleMapper.articleToArticleDto(article));
    }

    @Override
    public ArticleDto findById(long id) {
        return articleRepository.findById(id).map(article -> articleMapper.articleToArticleDto(article))
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public Page<ArticleDto> findByTopicId(long topicId, Pageable page) {
        return articleRepository.findByTopicId(topicId, page).map(
                article -> articleMapper.articleToArticleDto(article));
    }

    @Override
    public long count() {
        return articleRepository.count();
    }

    @Override
    public void create(ArticleCreateDto articleCreateDto) {
        articleRepository.save(articleMapper.articleCreateDtoToArticle(articleCreateDto));
    }

    @Override
    public void update(long id, ArticleUpdateDto articleUpdateDto) {
        Article article = articleRepository.findById(id).orElseThrow(NotFoundException::new);
        articleMapper.updateArticleFromDto(articleUpdateDto, article);
        articleRepository.save(article);
    }

    @Override
    public void deleteById(long id) {
        articleRepository.deleteById(id);
    }
}

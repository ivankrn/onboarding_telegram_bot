package com.ppteam.onboardingtelegrambot.service;

import com.ppteam.onboardingtelegrambot.controller.error.NotFoundException;
import com.ppteam.onboardingtelegrambot.database.Article;
import com.ppteam.onboardingtelegrambot.database.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;

    @Override
    public Page<Article> findAll(Pageable page) {
        return this.articleRepository.findAll(page);
    }

    @Override
    public Article findById(long id) {
        return this.articleRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public Page<Article> findByTopicId(long topicId, Pageable page) {
        return this.articleRepository.findByTopicId(topicId, page);
    }

    @Override
    public long count() {
        return this.articleRepository.count();
    }

    @Override
    public void save(Article article) {
        this.articleRepository.save(article);
    }

    @Override
    public void deleteById(long id) {
        this.articleRepository.deleteById(id);
    }
}

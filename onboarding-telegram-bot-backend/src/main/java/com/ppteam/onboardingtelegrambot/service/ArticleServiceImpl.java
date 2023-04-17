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
        return articleRepository.findAll(page);
    }

    @Override
    public Article findById(long id) {
        return articleRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public Article getReferenceById(long id) {
        return articleRepository.getReferenceById(id);
    }

    @Override
    public Page<Article> findByTopicId(long topicId, Pageable page) {
        return articleRepository.findByTopicId(topicId, page);
    }

    @Override
    public long count() {
        return articleRepository.count();
    }

    @Override
    public void save(Article article) {
        articleRepository.save(article);
    }

    @Override
    public void deleteById(long id) {
        articleRepository.deleteById(id);
    }
}

package com.ppteam.onboardingtelegrambot.service;

import com.ppteam.onboardingtelegrambot.controller.error.NotFoundException;
import com.ppteam.onboardingtelegrambot.database.ArticleStatistic;
import com.ppteam.onboardingtelegrambot.database.ArticleStatisticRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleStatisticServiceImpl implements ArticleStatisticService {

    private final ArticleStatisticRepository articleStatisticRepository;

    @Override
    public Page<ArticleStatistic> findAll(Pageable page) {
        return articleStatisticRepository.findAll(page);
    }

    @Override
    public ArticleStatistic findByArticleId(long articleId) {
        return articleStatisticRepository.findByArticleId(articleId);
    }

    @Override
    public ArticleStatistic findById(long id) {
        return articleStatisticRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public void save(ArticleStatistic articleStatistic) {
        articleStatisticRepository.save(articleStatistic);
    }

    @Override
    public boolean existsByArticleId(long articleId) {
        return articleStatisticRepository.existsByArticleId(articleId);
    }

    @Override
    public void deleteById(long id) {
        articleStatisticRepository.deleteById(id);
    }
}

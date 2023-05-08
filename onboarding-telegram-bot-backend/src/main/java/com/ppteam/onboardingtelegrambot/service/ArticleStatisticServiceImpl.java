package com.ppteam.onboardingtelegrambot.service;

import com.ppteam.onboardingtelegrambot.controller.error.NotFoundException;
import com.ppteam.onboardingtelegrambot.database.ArticleRepository;
import com.ppteam.onboardingtelegrambot.database.ArticleStatistic;
import com.ppteam.onboardingtelegrambot.database.ArticleStatisticRepository;
import com.ppteam.onboardingtelegrambot.dto.ArticleStatisticDto;
import com.ppteam.onboardingtelegrambot.dto.mappers.ArticleStatisticMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleStatisticServiceImpl implements ArticleStatisticService {

    private final ArticleStatisticRepository articleStatisticRepository;
    private final ArticleRepository articleRepository;
    private final ArticleStatisticMapper articleStatisticMapper;

    @Override
    public Page<ArticleStatisticDto> findAll(Pageable page) {
        return articleStatisticRepository.findAll(page).map(articleStatisticMapper::articleStatisticToArticleStatisticDto);
    }

    @Override
    public ArticleStatisticDto findById(long id) {
        return articleStatisticRepository.findById(id).map(articleStatisticMapper::articleStatisticToArticleStatisticDto)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void save(ArticleStatisticDto articleStatisticDto) {
        articleStatisticRepository.save(articleStatisticMapper.articleStatisticDtoToArticleStatistic(articleStatisticDto));
    }

    @Override
    public void deleteById(long id) {
        articleStatisticRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void updateForArticle(long articleId, int rating) {
        ArticleStatistic statistic;
        if (articleStatisticRepository.existsByArticleId(articleId)) {
            statistic = articleStatisticRepository.findByArticleId(articleId).get();
            statistic.setRatingsSum(statistic.getRatingsSum() + rating);
            statistic.setTotalRatingsCount(statistic.getTotalRatingsCount() + 1);
        } else {
            statistic = new ArticleStatistic();
            statistic.setArticle(articleRepository.getReferenceById(articleId));
            statistic.setRatingsSum(statistic.getRatingsSum() + rating);
            statistic.setTotalRatingsCount(statistic.getTotalRatingsCount() + 1);
            articleStatisticRepository.save(statistic);
        }
    }

}

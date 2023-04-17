package com.ppteam.onboardingtelegrambot.database;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArticleStatisticRepository extends PagingAndSortingRepository<ArticleStatistic, Long>,
        CrudRepository<ArticleStatistic, Long> {
    Optional<ArticleStatistic> findByArticleId(long articleId);
    boolean existsByArticleId(long articleId);
}

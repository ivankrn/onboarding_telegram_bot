package com.ppteam.onboardingtelegrambot.database;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleTopicRepository extends PagingAndSortingRepository<ArticleTopic, Long> {

}

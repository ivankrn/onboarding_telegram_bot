package com.ppteam.onboardingtelegrambot.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleTopicRepository extends JpaRepository<ArticleTopic, Long> {
}

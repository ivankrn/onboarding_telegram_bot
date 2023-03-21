package com.ppteam.onboardingtelegrambot.controller;

import com.ppteam.onboardingtelegrambot.database.Article;
import com.ppteam.onboardingtelegrambot.database.ArticleTopic;
import com.ppteam.onboardingtelegrambot.database.ArticleTopicRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/article_topics")
@CrossOrigin(origins = "http://localhost:4200")
public class ArticleTopicController {

    private final ArticleTopicRepository articleTopicRepository;

    public ArticleTopicController(ArticleTopicRepository articleTopicRepository) {
        this.articleTopicRepository = articleTopicRepository;
    }

    @GetMapping
    public List<ArticleTopic> getArticleTopics() {
        Pageable page = PageRequest.of(0, 20);
        return articleTopicRepository.findAll(page).getContent();
    }

}
package com.ppteam.onboardingtelegrambot.controller;

import com.ppteam.onboardingtelegrambot.database.ArticleTopic;
import com.ppteam.onboardingtelegrambot.dto.ArticleTopicDto;
import com.ppteam.onboardingtelegrambot.service.ArticleTopicService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class ArticleTopicController {

    private final ArticleTopicService articleTopicService;

    @GetMapping
    public List<ArticleTopicDto> getArticleTopics() {
        return articleTopicService.findAll();
    }

}

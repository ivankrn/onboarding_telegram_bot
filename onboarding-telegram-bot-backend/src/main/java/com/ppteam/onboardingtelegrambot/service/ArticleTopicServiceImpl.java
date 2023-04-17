package com.ppteam.onboardingtelegrambot.service;

import com.ppteam.onboardingtelegrambot.database.ArticleTopic;
import com.ppteam.onboardingtelegrambot.database.ArticleTopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ArticleTopicServiceImpl implements ArticleTopicService {
    private final ArticleTopicRepository articleTopicRepository;

    @Override
    public Page<ArticleTopic> findAll(Pageable page) {
        return articleTopicRepository.findAll(page);
    }
}

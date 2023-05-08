package com.ppteam.onboardingtelegrambot.service;

import com.ppteam.onboardingtelegrambot.database.ArticleTopicRepository;
import com.ppteam.onboardingtelegrambot.dto.ArticleTopicDto;
import com.ppteam.onboardingtelegrambot.dto.mappers.ArticleTopicMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ArticleTopicServiceImpl implements ArticleTopicService {
    private final ArticleTopicRepository articleTopicRepository;
    private final ArticleTopicMapper articleTopicMapper;

    @Override
    public List<ArticleTopicDto> findAll() {
        return articleTopicRepository.findAll().stream().map(articleTopicMapper::topicToTopicDto).collect(Collectors.toList());
    }

    @Override
    public void save(ArticleTopicDto topicDto) {
        articleTopicRepository.save(articleTopicMapper.topicDtoToTopic(topicDto));
    }

    @Override
    public void deleteById(long id) {
        articleTopicRepository.deleteById(id);
    }
}

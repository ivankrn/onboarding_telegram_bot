package com.ppteam.onboardingtelegrambot.service;

import com.ppteam.onboardingtelegrambot.database.ArticleTopicRepository;
import com.ppteam.onboardingtelegrambot.dto.ArticleTopicDto;
import com.ppteam.onboardingtelegrambot.dto.mappers.MapStructMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ArticleTopicServiceImpl implements ArticleTopicService {
    private final ArticleTopicRepository articleTopicRepository;
    private final MapStructMapper mapStructMapper;

    @Override
    public List<ArticleTopicDto> findAll() {
        return articleTopicRepository.findAll().stream().map(mapStructMapper::topicToTopicDto).collect(Collectors.toList());
    }

    @Override
    public void save(ArticleTopicDto topicDto) {
        articleTopicRepository.save(mapStructMapper.topicDtoToTopic(topicDto));
    }

    @Override
    public void deleteById(long id) {
        articleTopicRepository.deleteById(id);
    }
}

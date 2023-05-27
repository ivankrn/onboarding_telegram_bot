package com.ppteam.onboardingtelegrambot.service;

import com.ppteam.onboardingtelegrambot.controller.error.NotFoundException;
import com.ppteam.onboardingtelegrambot.database.ArticleTopic;
import com.ppteam.onboardingtelegrambot.database.ArticleTopicRepository;
import com.ppteam.onboardingtelegrambot.dto.ArticleTopicCreateDto;
import com.ppteam.onboardingtelegrambot.dto.ArticleTopicDto;
import com.ppteam.onboardingtelegrambot.dto.ArticleTopicPatchDto;
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
    public void create(ArticleTopicCreateDto topicDto) {
        articleTopicRepository.save(articleTopicMapper.topicCreateDtoToTopic(topicDto));
    }

    @Override
    public void updatePartial(long id, ArticleTopicPatchDto articleTopicPatchDto) {
        ArticleTopic articleTopic = articleTopicRepository.findById(id).orElseThrow(NotFoundException::new);
        articleTopicMapper.patchArticleTopicFromDto(articleTopicPatchDto, articleTopic);
        articleTopicRepository.save(articleTopic);
    }

    @Override
    public void deleteById(long id) {
        articleTopicRepository.deleteById(id);
    }
}

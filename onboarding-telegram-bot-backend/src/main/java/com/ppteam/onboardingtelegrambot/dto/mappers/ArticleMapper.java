package com.ppteam.onboardingtelegrambot.dto.mappers;

import com.ppteam.onboardingtelegrambot.database.Article;
import com.ppteam.onboardingtelegrambot.database.TestRepository;
import com.ppteam.onboardingtelegrambot.dto.ArticleCreateDto;
import com.ppteam.onboardingtelegrambot.dto.ArticleDto;
import com.ppteam.onboardingtelegrambot.dto.ArticleUpdateDto;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = {ArticleTopicMapper.class, TestRepository.class},
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public abstract class ArticleMapper {
    @Autowired
    private ArticleTopicMapper articleTopicMapper;
    @Autowired
    private TestRepository testRepository;

    @Mapping(source = "article.id", target = "id")
    @Mapping(source = "article.topic", target = "topic")
    @Mapping(source = "article.title", target = "title")
    @Mapping(source = "article.createdAt", target = "createdAt")
    @Mapping(source = "article.test.id", target = "testId")
    public abstract ArticleDto articleToArticleDto(Article article);

    public Article articleCreateDtoToArticle(ArticleCreateDto articleCreateDto) {
        if (articleCreateDto == null) {
            return null;
        }
        Article article = new Article();
        article.setTopic(articleTopicMapper.topicDtoToTopic(articleCreateDto.getTopic()));
        article.setTitle(articleCreateDto.getTitle());
        article.setContent(articleCreateDto.getContent());
        article.setUsefulLinks(articleCreateDto.getUsefulLinks());
        if (articleCreateDto.getTestId() != null) {
            article.setTest(testRepository.getReferenceById(articleCreateDto.getTestId()));
        } else {
            article.setTest(null);
        }
        return article;
    }

    public void updateArticleFromDto(ArticleUpdateDto articleUpdateDto, @MappingTarget Article article) {
        if (articleUpdateDto == null) {
            return;
        }
        article.setTopic(articleTopicMapper.topicDtoToTopic(articleUpdateDto.getTopic()));
        article.setTitle(articleUpdateDto.getTitle());
        article.setContent(articleUpdateDto.getContent());
        article.setUsefulLinks(articleUpdateDto.getUsefulLinks());
        if (articleUpdateDto.getTestId() != null) {
            article.setTest(testRepository.getReferenceById(articleUpdateDto.getTestId()));
        } else {
            article.setTest(null);
        }
    }
}

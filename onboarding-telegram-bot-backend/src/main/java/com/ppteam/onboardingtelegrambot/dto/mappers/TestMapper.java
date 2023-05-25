package com.ppteam.onboardingtelegrambot.dto.mappers;

import com.ppteam.onboardingtelegrambot.database.Test;
import com.ppteam.onboardingtelegrambot.dto.*;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = {ArticleTopicMapper.class, TestQuestionMapper.class},
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public abstract class TestMapper {
    @Autowired
    private ArticleTopicMapper articleTopicMapper;
    @Autowired
    private TestQuestionMapper testQuestionMapper;

    public abstract TestDto testToTestDto(Test test);

    public abstract TestFullDto testToTestFullDto(Test test);

    public abstract Test testCreateDtoToTest(TestCreateDto testCreateDto);

    public void updateTestFromDto(TestUpdateDto testUpdateDto, @MappingTarget Test test) {
        if (testUpdateDto == null) {
            return;
        }
        test.setTitle(testUpdateDto.getTitle());
        test.setTopic(articleTopicMapper.topicDtoToTopic(testUpdateDto.getTopic()));
        test.setDescription(testUpdateDto.getDescription());
        test.getQuestions().forEach(question -> question.setTest(null));
        test.getQuestions().clear();
        if (testUpdateDto.getQuestions() != null) {
            for (TestQuestionFullDto question : testUpdateDto.getQuestions()) {
                test.addQuestion(testQuestionMapper.testQuestionFullDtoToTestQuestion(question));
            }
        }
    }

    public void patchTestFromDto(TestPatchDto testPatchDto, @MappingTarget Test test) {
        if (testPatchDto == null) {
            return;
        }
        if (testPatchDto.getTopic() != null) {
            test.setTopic(articleTopicMapper.topicDtoToTopic(testPatchDto.getTopic()));
        }
        if (testPatchDto.getTitle() != null && testPatchDto.getTitle().isPresent()) {
            test.setTitle(testPatchDto.getTitle().get());
        }
        if (testPatchDto.getDescription() != null) {
            test.setDescription(testPatchDto.getDescription());
        }
        if (testPatchDto.getQuestions() != null) {
            test.getQuestions().forEach(question -> question.setTest(null));
            test.getQuestions().clear();
            for (TestQuestionFullDto question : testPatchDto.getQuestions()) {
                test.addQuestion(testQuestionMapper.testQuestionFullDtoToTestQuestion(question));
            }
        }
    }
}

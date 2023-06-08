package com.ppteam.onboardingtelegrambot.service;

import com.ppteam.onboardingtelegrambot.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TestService {
    Page<TestDto> findAll(Pageable page);

    Page<TestDto> findByTopicId(long topicId, Pageable page);

    TestDto findById(long id);
    TestFullDto findByIdWithQuestionsAndAnswers(long id);

    long count();

    void create(TestCreateDto testCreateDto);
    void update(long id, TestUpdateDto testUpdateDto);

    void updatePartial(long id, TestPatchDto testPatchDto);

    void deleteById(long id);
}

package com.ppteam.onboardingtelegrambot.service;

import com.ppteam.onboardingtelegrambot.dto.TestCreateDto;
import com.ppteam.onboardingtelegrambot.dto.TestDto;
import com.ppteam.onboardingtelegrambot.dto.TestFullDto;
import com.ppteam.onboardingtelegrambot.dto.TestUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TestService {
    Page<TestDto> findAll(Pageable page);

    Page<TestDto> findByTopicId(long topicId, Pageable page);

    TestFullDto findById(long id);

    long count();

    void create(TestCreateDto testCreateDto);
    void update(long id, TestUpdateDto testUpdateDto);

    void deleteById(long id);
}

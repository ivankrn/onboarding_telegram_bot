package com.ppteam.onboardingtelegrambot.service;

import com.ppteam.onboardingtelegrambot.dto.TestDto;
import com.ppteam.onboardingtelegrambot.dto.TestFullDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TestService {
    Page<TestDto> findAll(Pageable page);

    Page<TestDto> findByTopicId(long topicId, Pageable page);

    TestFullDto findById(long id);

    long count();

    void save(TestFullDto testFullDto);

    void deleteById(long id);
}

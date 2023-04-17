package com.ppteam.onboardingtelegrambot.service;

import com.ppteam.onboardingtelegrambot.dto.TestStatisticDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TestStatisticService {
    Page<TestStatisticDto> findAll(Pageable page);

    TestStatisticDto findById(long id);

    void save(TestStatisticDto testStatisticDto);

    void deleteById(long id);
    void updateForTest(long testId, int score);
}

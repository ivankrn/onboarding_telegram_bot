package com.ppteam.onboardingtelegrambot.service;

import com.ppteam.onboardingtelegrambot.database.TestStatistic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TestStatisticService {
    Page<TestStatistic> findAll(Pageable page);

    TestStatistic findByTestId(long testId);

    TestStatistic findById(long id);

    void save(TestStatistic testStatistic);
    boolean existsByTestId(long testId);
    void deleteById(long id);
}

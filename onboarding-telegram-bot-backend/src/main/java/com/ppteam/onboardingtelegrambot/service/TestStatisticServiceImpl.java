package com.ppteam.onboardingtelegrambot.service;

import com.ppteam.onboardingtelegrambot.controller.error.NotFoundException;
import com.ppteam.onboardingtelegrambot.database.TestStatistic;
import com.ppteam.onboardingtelegrambot.database.TestStatisticRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestStatisticServiceImpl implements TestStatisticService {

    private final TestStatisticRepository testStatisticRepository;

    @Override
    public Page<TestStatistic> findAll(Pageable page) {
        return testStatisticRepository.findAll(page);
    }

    @Override
    public TestStatistic findByTestId(long testId) {
        return testStatisticRepository.findByTestId(testId);
    }

    @Override
    public TestStatistic findById(long id) {
        return testStatisticRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public void save(TestStatistic testStatistic) {
        testStatisticRepository.save(testStatistic);
    }

    @Override
    public boolean existsByTestId(long testId) {
        return testStatisticRepository.existsByTestId(testId);
    }

    @Override
    public void deleteById(long id) {
        testStatisticRepository.deleteById(id);
    }
}

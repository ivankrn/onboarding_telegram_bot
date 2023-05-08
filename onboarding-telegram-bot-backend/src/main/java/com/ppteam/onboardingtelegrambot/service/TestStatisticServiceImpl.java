package com.ppteam.onboardingtelegrambot.service;

import com.ppteam.onboardingtelegrambot.controller.error.NotFoundException;
import com.ppteam.onboardingtelegrambot.database.Test;
import com.ppteam.onboardingtelegrambot.database.TestRepository;
import com.ppteam.onboardingtelegrambot.database.TestStatistic;
import com.ppteam.onboardingtelegrambot.database.TestStatisticRepository;
import com.ppteam.onboardingtelegrambot.dto.TestStatisticDto;
import com.ppteam.onboardingtelegrambot.dto.mappers.TestStatisticMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestStatisticServiceImpl implements TestStatisticService {

    private final TestStatisticRepository testStatisticRepository;
    private final TestRepository testRepository;
    private final TestStatisticMapper testStatisticMapper;

    @Override
    public Page<TestStatisticDto> findAll(Pageable page) {
        return testStatisticRepository.findAll(page).map(testStatisticMapper::testStatisticToTestStatisticDto);
    }

    @Override
    public TestStatisticDto findById(long id) {
        return testStatisticRepository.findById(id).map(testStatisticMapper::testStatisticToTestStatisticDto)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void save(TestStatisticDto testStatisticDto) {
        testStatisticRepository.save(testStatisticMapper.testStatisticDtoToTestStatistic(testStatisticDto));
    }

    @Override
    public void deleteById(long id) {
        testStatisticRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void updateForTest(long testId, int score) {
        TestStatistic statistic;
        Test test = testRepository.findById(testId).get();
        if (testStatisticRepository.existsByTestId(testId)) {
            statistic = testStatisticRepository.findByTestId(testId).get();
            statistic.setCorrectAnswersCount(statistic.getCorrectAnswersCount() + score);
            statistic.setTotalAnswersCount(statistic.getTotalAnswersCount() + test.getQuestions().size());
            statistic.setTotalAttemptsCount(statistic.getTotalAttemptsCount() + 1);
        } else {
            statistic = new TestStatistic();
            statistic.setTest(test);
            statistic.setCorrectAnswersCount(statistic.getCorrectAnswersCount() + score);
            statistic.setTotalAnswersCount(statistic.getTotalAnswersCount() + test.getQuestions().size());
            statistic.setTotalAttemptsCount(statistic.getTotalAttemptsCount() + 1);
            testStatisticRepository.save(statistic);
        }
    }
}

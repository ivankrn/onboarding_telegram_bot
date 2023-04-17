package com.ppteam.onboardingtelegrambot.database;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TestStatisticRepository extends PagingAndSortingRepository<TestStatistic, Long>,
        CrudRepository<TestStatistic, Long> {
    Optional<TestStatistic> findByTestId(long testId);
    boolean existsByTestId(long testId);
}

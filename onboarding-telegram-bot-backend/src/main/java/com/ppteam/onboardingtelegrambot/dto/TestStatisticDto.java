package com.ppteam.onboardingtelegrambot.dto;

import lombok.Data;

@Data
public class TestStatisticDto {
    private long id;
    private TestDto test;
    private long correctAnswersCount;
    private long totalAnswersCount;
    private long totalAttemptsCount;
}

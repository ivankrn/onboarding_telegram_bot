package com.ppteam.onboardingtelegrambot.dto;

import lombok.Data;

@Data
public class TestStatisticDto {
    private long id;
    private String testTitle;
    private long correctAnswersCount;
    private long totalAnswersCount;
    private long totalAttemptsCount;
}

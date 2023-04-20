package com.ppteam.onboardingtelegrambot.dto;

import lombok.Data;

@Data
public class TestSessionDto {
    private long userId;
    private long testId;
    private int score;
}

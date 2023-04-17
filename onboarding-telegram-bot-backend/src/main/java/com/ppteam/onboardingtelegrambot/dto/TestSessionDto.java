package com.ppteam.onboardingtelegrambot.dto;

import lombok.Data;

import java.util.Set;

@Data
public class TestSessionDto {
    private long id;
    private long userId;
    private long testId;
    private int score;
}

package com.ppteam.onboardingtelegrambot.dto;

import lombok.Data;

@Data
public class TestSessionPassedQuestionDto {
    private long id;
    private long questionId;
    private TestSessionDto testSession;
}

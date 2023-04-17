package com.ppteam.onboardingtelegrambot.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TestAnswerDto {
    private long id;
    @NotBlank
    private String answer;
    @NotNull
    private boolean isCorrect;
}

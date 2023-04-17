package com.ppteam.onboardingtelegrambot.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TestQuestionDto {
    private long id;
    @NotBlank
    private String question;
}

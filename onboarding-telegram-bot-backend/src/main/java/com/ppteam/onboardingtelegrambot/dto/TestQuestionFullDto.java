package com.ppteam.onboardingtelegrambot.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Set;

@Data
public class TestQuestionFullDto {
    private long id;
    @NotBlank
    private String question;
    @NotEmpty
    private Set<TestAnswerDto> answers;
}

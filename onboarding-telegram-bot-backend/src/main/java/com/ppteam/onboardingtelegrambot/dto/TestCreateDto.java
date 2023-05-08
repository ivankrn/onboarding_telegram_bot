package com.ppteam.onboardingtelegrambot.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;

@Data
public class TestCreateDto {
    @NotBlank
    private String title;
    @NotNull
    private ArticleTopicDto topic;
    private String description;
    @NotEmpty
    private Set<TestQuestionFullDto> questions;
}

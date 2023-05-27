package com.ppteam.onboardingtelegrambot.dto;

import com.ppteam.onboardingtelegrambot.validators.NullOrNotBlank;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;

@Data
public class TestUpdateDto {
    @NotBlank
    private String title;
    @NotNull
    private ArticleTopicDto topic;
    @NullOrNotBlank
    private String description;
    @NotEmpty
    private Set<TestQuestionFullDto> questions;
}

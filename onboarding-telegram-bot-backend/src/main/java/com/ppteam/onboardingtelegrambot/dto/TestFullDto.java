package com.ppteam.onboardingtelegrambot.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.Set;

@Data
public class TestFullDto {
    private long id;
    @NotBlank
    private String title;
    @NotNull
    private ArticleTopicDto topic;
    private String description;
    private OffsetDateTime createdAt;
    @NotEmpty
    private Set<TestQuestionFullDto> questions;
}

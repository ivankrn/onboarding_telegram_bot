package com.ppteam.onboardingtelegrambot.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class TestDto {
    private long id;
    @NotBlank
    private String title;
    @NotNull
    private ArticleTopicDto topic;
    private String description;
    private OffsetDateTime createdAt;
}

package com.ppteam.onboardingtelegrambot.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class ArticleDto {
    private long id;
    @NotNull
    private ArticleTopicDto topic;
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    private String usefulLinks;
    private OffsetDateTime createdAt;
    private TestDto test;
}

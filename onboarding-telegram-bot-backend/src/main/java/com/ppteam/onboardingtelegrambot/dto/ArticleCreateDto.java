package com.ppteam.onboardingtelegrambot.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ArticleCreateDto {
    @NotNull
    private ArticleTopicDto topic;
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    private String usefulLinks;
    private Long testId;
}

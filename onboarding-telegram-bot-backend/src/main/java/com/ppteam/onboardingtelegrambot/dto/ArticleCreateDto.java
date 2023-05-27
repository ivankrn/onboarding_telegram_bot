package com.ppteam.onboardingtelegrambot.dto;

import com.ppteam.onboardingtelegrambot.validators.NullOrNotBlank;
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
    @NullOrNotBlank
    private String usefulLinks;
    private Long testId;
}

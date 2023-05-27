package com.ppteam.onboardingtelegrambot.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ArticleTopicCreateDto {
    @NotBlank
    private String name;
}

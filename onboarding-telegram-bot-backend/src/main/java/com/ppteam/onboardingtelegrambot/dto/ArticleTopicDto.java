package com.ppteam.onboardingtelegrambot.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ArticleTopicDto {
    private long id;
    @NotBlank
    private String name;
}

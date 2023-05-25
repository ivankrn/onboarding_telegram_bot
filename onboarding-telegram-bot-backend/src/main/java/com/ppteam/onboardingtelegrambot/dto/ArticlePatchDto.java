package com.ppteam.onboardingtelegrambot.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Optional;

@Data
public class ArticlePatchDto {
    private ArticleTopicDto topic;
    private Optional<@NotBlank String> title;
    private Optional<@NotBlank String>  content;
    private String usefulLinks;
    private Optional<Long> testId;
}

package com.ppteam.onboardingtelegrambot.dto;

import com.ppteam.onboardingtelegrambot.validators.NullOrNotBlank;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Optional;

@Data
public class ArticlePatchDto {
    private ArticleTopicDto topic;
    @NullOrNotBlank
    private String title;
    @NullOrNotBlank
    private String content;
    private Optional<@NullOrNotBlank String> usefulLinks;
    private Optional<Long> testId;
}

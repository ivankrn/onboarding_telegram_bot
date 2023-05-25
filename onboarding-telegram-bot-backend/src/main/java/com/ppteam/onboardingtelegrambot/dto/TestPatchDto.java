package com.ppteam.onboardingtelegrambot.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Optional;
import java.util.Set;

@Data
public class TestPatchDto {
    private Optional<@NotBlank String> title;
    private ArticleTopicDto topic;
    private String description;
    private Set<TestQuestionFullDto> questions;
}

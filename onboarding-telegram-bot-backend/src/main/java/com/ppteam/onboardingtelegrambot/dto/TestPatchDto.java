package com.ppteam.onboardingtelegrambot.dto;

import com.ppteam.onboardingtelegrambot.validators.NullOrNotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Optional;
import java.util.Set;

@Data
public class TestPatchDto {
    @NullOrNotBlank
    private String title;
    private ArticleTopicDto topic;
    private Optional<@NullOrNotBlank String> description;
    private Optional<@NotEmpty Set<TestQuestionFullDto>> questions;
}

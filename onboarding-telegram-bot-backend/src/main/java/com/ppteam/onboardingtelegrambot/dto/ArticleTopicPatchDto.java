package com.ppteam.onboardingtelegrambot.dto;

import com.ppteam.onboardingtelegrambot.validators.NullOrNotBlank;
import lombok.Data;

@Data
public class ArticleTopicPatchDto {
    @NullOrNotBlank
    private String name;
}

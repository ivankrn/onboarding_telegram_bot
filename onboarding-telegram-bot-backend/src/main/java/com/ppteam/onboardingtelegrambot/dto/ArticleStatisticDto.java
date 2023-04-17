package com.ppteam.onboardingtelegrambot.dto;

import lombok.Data;

@Data
public class ArticleStatisticDto {
    private long id;
    private ArticleDto article;
    private long ratingsSum;
    private long totalRatingsCount;
}

package com.ppteam.onboardingtelegrambot.dto;

import lombok.Data;

@Data
public class ArticleStatisticDto {
    private long id;
    private String articleTitle;
    private long ratingsSum;
    private long totalRatingsCount;
}

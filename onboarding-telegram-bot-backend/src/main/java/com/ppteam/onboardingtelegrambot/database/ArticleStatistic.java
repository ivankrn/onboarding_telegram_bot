package com.ppteam.onboardingtelegrambot.database;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@Table(name = "article_statistic")
@EqualsAndHashCode(exclude = "article")
@ToString(exclude = "article")
public class ArticleStatistic {
    @Id
    private long id;
    @OneToOne
    @JoinColumn(name = "article_id", nullable = false)
    @MapsId
    private Article article;
    @NotNull
    @Column(name = "ratings_sum")
    private long ratingsSum;
    @NotNull
    @Column(name = "total_ratings_count")
    private long totalRatingsCount;
}

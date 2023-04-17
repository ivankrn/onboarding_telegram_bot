package com.ppteam.onboardingtelegrambot.database;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "article_statistic")
public class ArticleStatistic {
    @Id
    @SequenceGenerator(name = "article_statistic_id_seq", sequenceName = "article_statistic_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "article_statistic_id_seq")
    private long id;
    @OneToOne
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;
    @NotNull
    @Column(name = "ratings_sum")
    private long ratingsSum;
    @NotNull
    @Column(name = "total_ratings_count")
    private long totalRatingsCount;
}

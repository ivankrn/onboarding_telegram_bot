package com.ppteam.onboardingtelegrambot.database;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "test_statistic")
public class TestStatistic {
    @Id
    private long id;
    @OneToOne
    @JoinColumn(name = "test_id", nullable = false)
    @MapsId
    private Test test;
    @NotNull
    @Column(name = "correct_answers_count")
    private long correctAnswersCount;
    @NotNull
    @Column(name = "total_answers_count")
    private long totalAnswersCount;
    @NotNull
    @Column(name = "total_attempts_count")
    private long totalAttemptsCount;
}

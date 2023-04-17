package com.ppteam.onboardingtelegrambot.database;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "test_statistic")
public class TestStatistic {
    @Id
    @SequenceGenerator(name = "test_statistic_id_seq", sequenceName = "test_statistic_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "test_statistic_id_seq")
    private long id;
    @OneToOne
    @JoinColumn(name = "test_id", nullable = false)
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

package com.ppteam.onboardingtelegrambot.database;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;


@Data
@Entity
@EqualsAndHashCode(exclude = "passedQuestions")
@ToString(exclude = "passedQuestions")
@Table(name = "test_session")
public class TestSession {
    @Id
    private long userId;
    @NotNull
    @Column(name = "test_id")
    private long testId;
    @NotNull
    @Column
    private int score;
    @OneToMany(mappedBy = "testSession", cascade = CascadeType.REMOVE)
    private Set<TestSessionPassedQuestion> passedQuestions = new HashSet<>();
}

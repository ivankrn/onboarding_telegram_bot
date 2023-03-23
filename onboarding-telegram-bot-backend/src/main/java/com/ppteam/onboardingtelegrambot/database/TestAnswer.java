package com.ppteam.onboardingtelegrambot.database;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@Data
@Entity
@EqualsAndHashCode(exclude = "testQuestion")
@ToString(exclude = "testQuestion")
@Table(name = "test_answer")
public class TestAnswer {
    @Id
    @SequenceGenerator(name = "test_answer_id_seq", sequenceName = "test_answer_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "test_answer_id_seq")
    private long id;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "test_question_id")
    private TestQuestion testQuestion;
    @NotBlank
    @Column(nullable = false)
    private String answer;
}

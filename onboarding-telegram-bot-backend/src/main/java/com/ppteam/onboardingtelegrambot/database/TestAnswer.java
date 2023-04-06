package com.ppteam.onboardingtelegrambot.database;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @ManyToOne
    @JoinColumn(name = "test_question_id", nullable = false)
    @JsonBackReference(value = "test-answers")
    private TestQuestion testQuestion;
    @NotBlank
    @Column(nullable = false)
    private String answer;
    @NotNull
    @Column(name = "is_correct")
    private boolean isCorrect;
}

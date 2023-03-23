package com.ppteam.onboardingtelegrambot.database;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;


@Data
@Entity
@EqualsAndHashCode(exclude = {"test", "answers"})
@ToString(exclude = {"test", "answers"})
@Table(name = "test_question")
public class TestQuestion {
    @Id
    @SequenceGenerator(name = "test_question_id_seq", sequenceName = "test_question_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "test_question_id_seq")
    private int id;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "test_id")
    private Test test;
    @NotBlank
    @Column(nullable = false)
    private String question;
    @OneToMany(mappedBy = "testQuestion")
    private Set<TestAnswer> answers = new HashSet<>();
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "correct_answer_id", referencedColumnName = "id")
    private TestAnswer correctAnswer;
}

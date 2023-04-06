package com.ppteam.onboardingtelegrambot.database;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
    private long id;
    @ManyToOne
    @JoinColumn(name = "test_id", nullable = false)
    @JsonBackReference(value = "test-questions")
    private Test test;
    @NotBlank
    @Column(nullable = false)
    private String question;
    @OneToMany(mappedBy = "testQuestion", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "test-answers")
    private Set<TestAnswer> answers = new HashSet<>();
}

package com.ppteam.onboardingtelegrambot.database;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@Data
@Entity
@EqualsAndHashCode(exclude = "testSession")
@ToString(exclude = "testSession")
@Table(name = "test_session_passed_question")
public class TestSessionPassedQuestion {
    @Id
    @SequenceGenerator(name = "test_session_passed_question_id_seq", sequenceName = "test_session_passed_question_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "test_session_passed_question_id_seq")
    private long id;
    @NotNull
    @Column(name = "question_id")
    private long questionId;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "test_session_id")
    private TestSession testSession;
}

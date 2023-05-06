package com.ppteam.onboardingtelegrambot.database;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface TestAnswerRepository extends CrudRepository<TestAnswer, Long> {
    @Query("select ta from TestAnswer ta where ta.testQuestion.id = ?1 and ta.isCorrect = true")
    Optional<TestAnswer> findCorrectAnswerForQuestionId(long questionId);

    @Query("select ta from TestAnswer ta where ta.testQuestion.id = ?1 and ta.isCorrect = true")
    Optional<Set<TestAnswer>> findCorrectAnswersForQuestionId(long questionId);
}

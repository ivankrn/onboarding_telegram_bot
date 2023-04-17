package com.ppteam.onboardingtelegrambot.database;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TestQuestionRepository extends CrudRepository<TestQuestion, Long> {
    List<TestQuestion> findByTestId(long testId);

    @Query("select q from TestQuestion q join fetch q.answers where q.id = ?1")
    Optional<TestQuestion> findByIdWithAnswers(long id);
}

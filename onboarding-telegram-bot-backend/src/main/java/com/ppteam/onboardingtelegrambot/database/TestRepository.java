package com.ppteam.onboardingtelegrambot.database;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {
    Page<Test> findByTopicId(long topicId, Pageable page);

    @EntityGraph(attributePaths = {"topic", "questions", "questions.answers"})
    Optional<Test> findById(long id);
}

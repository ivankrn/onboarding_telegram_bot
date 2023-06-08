package com.ppteam.onboardingtelegrambot.database;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface TestRepository extends JpaRepository<Test, Long> {
    Page<Test> findByTopicId(long topicId, Pageable page);

    @Query("select t from Test t join fetch t.questions q join fetch q.answers a where t.id = ?1")
    Optional<Test> findByIdWithQuestionsAndAnswers(long id);
}

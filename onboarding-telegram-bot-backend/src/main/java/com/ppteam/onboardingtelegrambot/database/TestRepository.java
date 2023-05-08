package com.ppteam.onboardingtelegrambot.database;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TestRepository extends JpaRepository<Test, Long> {
    Page<Test> findByTopicId(long topicId, Pageable page);
}

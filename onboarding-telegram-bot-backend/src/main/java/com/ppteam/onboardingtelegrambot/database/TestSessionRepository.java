package com.ppteam.onboardingtelegrambot.database;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TestSessionRepository extends JpaRepository<TestSession, Long> {
    Optional<TestSession> findByUserId(long userId);

    @Query("UPDATE TestSession s set s.score = s.score + 1 where s.id = ?1")
    @Modifying
    void increaseScore(long id);
}

package com.ppteam.onboardingtelegrambot.service;

import com.ppteam.onboardingtelegrambot.database.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TestService {
    Page<Test> findAll(Pageable page);
    Page<Test> findByTopicId(long topicId, Pageable page);
    Test findById(long id);
    Test getReferenceById(long id);
    long count();
    void save(Test test);
    void deleteById(long id);
}

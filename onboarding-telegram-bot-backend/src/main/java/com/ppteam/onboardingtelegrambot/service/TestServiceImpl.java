package com.ppteam.onboardingtelegrambot.service;

import com.ppteam.onboardingtelegrambot.controller.error.NotFoundException;
import com.ppteam.onboardingtelegrambot.database.Test;
import com.ppteam.onboardingtelegrambot.database.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {
    private final TestRepository testRepository;

    public Page<Test> findAll(Pageable page) {
        return this.testRepository.findAll(page);
    }

    public Page<Test> findByTopicId(long topicId, Pageable page) {
        return this.testRepository.findByTopicId(topicId, page);
    }

    public Test findById(long id) {
        return this.testRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public Test getReferenceById(long id) {
        return testRepository.getReferenceById(id);
    }

    public long count() {
        return this.testRepository.count();
    }

    public void save(Test test) {
        this.testRepository.save(test);
    }

    public void deleteById(long id) {
        this.testRepository.deleteById(id);
    }
}

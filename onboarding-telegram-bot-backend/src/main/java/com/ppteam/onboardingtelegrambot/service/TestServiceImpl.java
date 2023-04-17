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

    @Override
    public Page<Test> findAll(Pageable page) {
        return testRepository.findAll(page);
    }

    @Override
    public Page<Test> findByTopicId(long topicId, Pageable page) {
        return testRepository.findByTopicId(topicId, page);
    }

    @Override
    public Test findById(long id) {
        return testRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public Test getReferenceById(long id) {
        return testRepository.getReferenceById(id);
    }

    @Override
    public long count() {
        return testRepository.count();
    }

    @Override
    public void save(Test test) {
        testRepository.save(test);
    }

    @Override
    public void deleteById(long id) {
        testRepository.deleteById(id);
    }
}

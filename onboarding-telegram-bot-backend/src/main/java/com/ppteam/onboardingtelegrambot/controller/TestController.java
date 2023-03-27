package com.ppteam.onboardingtelegrambot.controller;

import com.ppteam.onboardingtelegrambot.database.Test;
import com.ppteam.onboardingtelegrambot.database.TestRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tests")
@CrossOrigin(origins = "http://localhost:4200")
public class TestController {
    private final TestRepository testRepository;

    public TestController(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    @GetMapping
    public List<Test> getTests() {
        Pageable page = PageRequest.of(0, 10);
        return testRepository.findAll(page).getContent();
    }

    @GetMapping("/count")
    public long getTestCount() {
        return testRepository.count();
    }
}

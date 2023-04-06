package com.ppteam.onboardingtelegrambot.controller;

import com.ppteam.onboardingtelegrambot.database.Test;
import com.ppteam.onboardingtelegrambot.service.TestService;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tests")
@CrossOrigin(origins = "http://localhost:4200")
public class TestController {
    private final TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping
    public List<Test> getTests() {
        Pageable page = PageRequest.of(0, 10);
        return testService.findAll(page).getContent();
    }

    @GetMapping("/{id}")
    public Test getTest(@PathVariable long id) {
        return testService.findById(id);
    }

    @GetMapping("/count")
    public long getTestCount() {
        return testService.count();
    }

    @PostMapping
    public void saveTest(@RequestBody @Valid Test test) {
        testService.save(test);
    }

    @DeleteMapping("/{id}")
    public void deleteTest(@PathVariable long id) {
        testService.deleteById(id);
    }
}

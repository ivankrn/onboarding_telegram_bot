package com.ppteam.onboardingtelegrambot.controller;

import com.ppteam.onboardingtelegrambot.dto.*;
import com.ppteam.onboardingtelegrambot.service.TestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tests")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class TestController {
    private final TestService testService;

    @GetMapping
    public Page<TestDto> getTests(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable paging = PageRequest.of(page, size);
        return testService.findAll(paging);
    }

    @GetMapping("/{id}")
    public TestFullDto getTest(@PathVariable long id) {
        return testService.findByIdWithQuestionsAndAnswers(id);
    }

    @GetMapping("/count")
    public long getTestCount() {
        return testService.count();
    }

    @PostMapping
    public void createTest(@RequestBody @Valid TestCreateDto testCreateDto) {
        testService.create(testCreateDto);
    }

    @PutMapping("/{id}")
    public void updateTest(@PathVariable long id, @RequestBody @Valid TestUpdateDto testUpdateDto) {
        testService.update(id, testUpdateDto);
    }

    @PatchMapping("/{id}")
    public void patchTest(@PathVariable long id, @RequestBody @Valid TestPatchDto testPatchDto) {
        testService.updatePartial(id, testPatchDto);
    }

    @DeleteMapping("/{id}")
    public void deleteTest(@PathVariable long id) {
        testService.deleteById(id);
    }
}

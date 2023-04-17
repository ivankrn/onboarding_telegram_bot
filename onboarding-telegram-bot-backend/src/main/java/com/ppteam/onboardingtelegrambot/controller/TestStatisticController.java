package com.ppteam.onboardingtelegrambot.controller;

import com.ppteam.onboardingtelegrambot.dto.TestStatisticDto;
import com.ppteam.onboardingtelegrambot.service.TestStatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tests/statistics")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class TestStatisticController {

    private final TestStatisticService testStatisticService;

    @GetMapping
    public Page<TestStatisticDto> getStatistics(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable paging = PageRequest.of(page, size);
        return testStatisticService.findAll(paging);
    }

    @GetMapping("/{id}")
    public TestStatisticDto getStatistic(@PathVariable long id) {
        return testStatisticService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteStatistic(@PathVariable long id) {
        testStatisticService.deleteById(id);
    }
}

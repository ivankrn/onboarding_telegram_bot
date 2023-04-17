package com.ppteam.onboardingtelegrambot.controller;

import com.ppteam.onboardingtelegrambot.database.ArticleStatistic;
import com.ppteam.onboardingtelegrambot.service.ArticleStatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/articles/statistics")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class ArticleStatisticController {

    private final ArticleStatisticService articleStatisticService;

    @GetMapping
    public Page<ArticleStatistic> getStatistics(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable paging = PageRequest.of(page, size);
        return articleStatisticService.findAll(paging);
    }

    @GetMapping("/{id}")
    public ArticleStatistic getStatistic(@PathVariable long id) {
        return articleStatisticService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteStatistic(@PathVariable long id) {
        articleStatisticService.deleteById(id);
    }
}

package com.ppteam.onboardingtelegrambot.controller;

import com.ppteam.onboardingtelegrambot.dto.ArticleTopicDto;
import com.ppteam.onboardingtelegrambot.service.ArticleTopicService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/article-topics")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class ArticleTopicController {

    private final ArticleTopicService articleTopicService;

    @GetMapping
    public List<ArticleTopicDto> getArticleTopics() {
        return articleTopicService.findAll();
    }

    @PostMapping
    public void saveTopic(@RequestBody @Valid ArticleTopicDto topic) {
        articleTopicService.save(topic);
    }

    @DeleteMapping("/{id}")
    public void deleteTopic(@PathVariable long id) {
        articleTopicService.deleteById(id);
    }
}

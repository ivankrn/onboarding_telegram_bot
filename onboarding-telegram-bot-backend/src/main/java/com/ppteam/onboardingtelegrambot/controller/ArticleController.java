package com.ppteam.onboardingtelegrambot.controller;

import com.ppteam.onboardingtelegrambot.database.Article;
import com.ppteam.onboardingtelegrambot.service.ArticleService;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
@CrossOrigin(origins = "http://localhost:4200")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public List<Article> getArticles() {
        Pageable page = PageRequest.of(0, 10);
        return articleService.findAll(page).getContent();
    }

    @GetMapping("/count")
    public long getArticleCount() {
        return articleService.count();
    }

    @GetMapping("/{id}")
    public Article getArticle(@PathVariable long id) {
        return articleService.findById(id);
    }

    @PostMapping
    public void saveArticle(@RequestBody @Valid Article article) {
        articleService.save(article);
    }

    @DeleteMapping("/{id}")
    public void deleteArticle(@PathVariable long id) {
        articleService.deleteById(id);
    }
}

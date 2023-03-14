package com.ppteam.onboardingtelegrambot.controller;

import com.ppteam.onboardingtelegrambot.database.Article;
import com.ppteam.onboardingtelegrambot.database.ArticleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ArticleController {

    private final ArticleRepository articleRepository;

    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @GetMapping("/articles")
    public List<Article> getArticles() {
        Pageable page = PageRequest.of(0, 10);
        return articleRepository.findAll(page).getContent();
    }

    @PostMapping("/articles")
    public void addArticle(@RequestBody Article article) {
        articleRepository.save(article);
    }
}

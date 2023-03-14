package com.ppteam.onboardingtelegrambot.controller;

import com.ppteam.onboardingtelegrambot.database.Article;
import com.ppteam.onboardingtelegrambot.database.ArticleRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
@CrossOrigin(origins = "http://localhost:4200")
public class ArticleController {

    private final ArticleRepository articleRepository;

    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @GetMapping
    public List<Article> getArticles() {
        Pageable page = PageRequest.of(0, 10);
        return articleRepository.findAll(page).getContent();
    }

    @GetMapping("/{id}")
    public Article getArticle(@PathVariable int id) {
        return articleRepository.findById(id);
    }

    @PostMapping
    public void addArticle(@RequestBody Article article) {
        articleRepository.save(article);
    }

    @DeleteMapping("/{id}")
    public void deleteArticle(@PathVariable int id) {
        System.out.println("inside " + id);
        articleRepository.deleteById((long)id);
    }
}

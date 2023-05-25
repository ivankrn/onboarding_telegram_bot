package com.ppteam.onboardingtelegrambot.controller;

import com.ppteam.onboardingtelegrambot.dto.ArticleCreateDto;
import com.ppteam.onboardingtelegrambot.dto.ArticleDto;
import com.ppteam.onboardingtelegrambot.dto.ArticlePatchDto;
import com.ppteam.onboardingtelegrambot.dto.ArticleUpdateDto;
import com.ppteam.onboardingtelegrambot.service.ArticleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/articles")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping
    public Page<ArticleDto> getArticles(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable paging = PageRequest.of(page, size);
        return articleService.findAll(paging);
    }

    @GetMapping("/count")
    public long getArticleCount() {
        return articleService.count();
    }

    @GetMapping("/{id}")
    public ArticleDto getArticle(@PathVariable long id) {
        return articleService.findById(id);
    }

    @PostMapping
    public void createArticle(@RequestBody @Valid ArticleCreateDto articleCreateDto) {
        articleService.create(articleCreateDto);
    }

    @PutMapping("/{id}")
    public void updateArticle(@PathVariable long id, @RequestBody @Valid ArticleUpdateDto articleUpdateDto) {
        articleService.update(id, articleUpdateDto);
    }

    @PatchMapping("/{id}")
    public void patchArticle(@PathVariable long id, @RequestBody @Valid ArticlePatchDto articlePatchDto) {
        articleService.updatePartial(id, articlePatchDto);
    }

    @DeleteMapping("/{id}")
    public void deleteArticle(@PathVariable long id) {
        articleService.deleteById(id);
    }
}

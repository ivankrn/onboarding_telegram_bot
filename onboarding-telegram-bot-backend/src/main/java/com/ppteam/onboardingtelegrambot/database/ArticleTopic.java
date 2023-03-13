package com.ppteam.onboardingtelegrambot.database;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@Table(name = "article_topic")
public class ArticleTopic {
    @Id
    private long id;
    @Column(nullable = false)
    @NotBlank
    private String name;
}

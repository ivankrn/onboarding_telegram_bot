package com.ppteam.onboardingtelegrambot.database;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Entity
@Table(name = "article")
public class Article extends Material {
    @Id
    @SequenceGenerator(name = "article_id_seq", sequenceName = "article_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "article_id_seq")
    private long id;
    @ManyToOne
    @JoinColumn(name = "topic_id", nullable = false)
    private ArticleTopic topic;
    @Column(nullable = false)
    @NotBlank
    private String title;
    @Column(nullable = false)
    @NotBlank
    private String content;
    @Column(name = "useful_links")
    private String usefulLinks;
    @Column(name = "test_link")
    private String testLink;
    @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
    private OffsetDateTime createdAt;
}

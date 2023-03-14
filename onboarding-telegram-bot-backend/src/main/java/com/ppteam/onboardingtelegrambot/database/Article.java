package com.ppteam.onboardingtelegrambot.database;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Entity
@Table(name = "article")
public class Article {
    @Id
    @SequenceGenerator(name = "article_id_seq", sequenceName = "article_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "article_id_seq")
    private long id;
    @NotNull
    @Column(name = "topic_id")
    private long topicId;
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
    @Column(name = "created_on", nullable = false, insertable = false, updatable = false)
    private OffsetDateTime createdOn;
}

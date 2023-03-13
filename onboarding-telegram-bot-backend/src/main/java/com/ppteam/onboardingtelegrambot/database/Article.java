package com.ppteam.onboardingtelegrambot.database;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Entity
@Table(name = "article")
public class Article {
    @Id
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
    @NotNull
    @Column(name = "created_on", nullable = false)
    private OffsetDateTime createdOn;
}

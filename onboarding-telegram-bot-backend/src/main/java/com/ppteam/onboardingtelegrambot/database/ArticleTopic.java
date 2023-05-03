package com.ppteam.onboardingtelegrambot.database;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "article_topic")
@EqualsAndHashCode(exclude = {"articles", "tests"})
@ToString(exclude = {"articles", "tests"})
public class ArticleTopic {
    @Id
    @SequenceGenerator(name = "article_topic_id_seq", sequenceName = "article_topic_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "article_topic_id_seq")
    private long id;
    @Column(nullable = false, unique = true)
    @NotBlank
    private String name;
    @OneToMany(mappedBy = "topic", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Article> articles = new HashSet<>();
    @OneToMany(mappedBy = "topic", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Test> tests = new HashSet<>();
}

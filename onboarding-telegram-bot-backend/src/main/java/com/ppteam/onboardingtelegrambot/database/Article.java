package com.ppteam.onboardingtelegrambot.database;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.OffsetDateTime;

@Data
@Entity
@Table(name = "article")
@EqualsAndHashCode(exclude = "statistic")
@ToString(exclude = "statistic")
public class Article {
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
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_id", foreignKey = @ForeignKey(name = "fk_test", foreignKeyDefinition = "foreign key (test_id) references test(id) on delete set null"))
    private Test test;
    @Column(name = "created_at", nullable = false, insertable = false, updatable = false,
            columnDefinition = "timestamp with time zone default current_timestamp")
    private OffsetDateTime createdAt;
    @OneToOne(mappedBy = "article", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private ArticleStatistic statistic;
}

package com.ppteam.onboardingtelegrambot.database;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@EqualsAndHashCode(exclude = "questions")
@ToString(exclude = "questions")
@Table(name = "test")
public class Test {
    @Id
    @SequenceGenerator(name = "test_id_seq", sequenceName = "test_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "test_id_seq")
    private long id;
    @ManyToOne
    @JoinColumn(name = "topic_id", nullable = false)
    private ArticleTopic topic;
    @Column(nullable = false)
    @NotBlank
    private String title;
    private String description;
    @NotEmpty
    @OneToMany(mappedBy = "test", cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.EXTRA)
    private Set<TestQuestion> questions = new HashSet<>();
    @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
    private OffsetDateTime createdAt;
    @OneToOne(mappedBy = "test", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private TestStatistic statistic;

    public void addQuestion(TestQuestion testQuestion) {
        this.questions.add(testQuestion);
        testQuestion.setTest(this);
    }
}


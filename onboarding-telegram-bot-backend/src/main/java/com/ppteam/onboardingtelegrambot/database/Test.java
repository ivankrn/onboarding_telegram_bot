package com.ppteam.onboardingtelegrambot.database;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@EqualsAndHashCode(exclude = "questions")
@ToString(exclude = "questions")
@Table(name = "test")
//@NamedEntityGraph(
//        name = "test-entity-graph-with-questions-answers",
//        attributeNodes = @NamedAttributeNode("questions"),
//        subgraphs = {
//                @NamedSubgraph(
//                        name = "questions-subgraph",
//                        attributeNodes = @NamedAttributeNode("answers")
//                )
//        }
//)
public class Test extends Material {
    @Id
    @SequenceGenerator(name = "test_id_seq", sequenceName = "test_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "test_id_seq")
    private long id;
    @NotNull
    @Column(name = "topic_id")
    private long topicId;
    @Column(name = "article_id")
    private Long articleId;
    @Column(nullable = false)
    @NotBlank
    private String title;
    @Column(nullable = false)
    @NotBlank
    private String description;
    @OneToMany(mappedBy = "test")
    private Set<TestQuestion> questions = new HashSet<>();
    @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
    private OffsetDateTime createdAt;
}


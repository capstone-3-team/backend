package com.example.QuickThink.Card.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.sql.Date;
import java.util.Set;


@Entity
@Getter
@Setter
public class CardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cardId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Set<String> hashTags;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Date writtenDate;

    @Column(nullable = false)
    private Date latestReviewDate;

    @Column(nullable = false)
    private Long reviewCount;
}

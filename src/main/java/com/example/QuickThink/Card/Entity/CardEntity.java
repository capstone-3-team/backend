package com.example.QuickThink.Card.Entity;

import com.example.QuickThink.Google.Entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "card")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="card_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 65535)
    private String content;

    @Column(nullable = false)
    @Builder.Default
    private HashSet<String> hashTags = new HashSet<>();

    @Column(nullable = false)
    private LocalDateTime writtenDate;

    @Column(nullable = false)
    private LocalDateTime latestReviewDate;

    @Column(nullable = false)
    private Long reviewCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;
}

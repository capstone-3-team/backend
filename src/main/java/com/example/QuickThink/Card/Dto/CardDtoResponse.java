package com.example.QuickThink.Card.Dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CardDtoResponse {
    private String title;
    private String content;
    private Set<String> hashTags;
    private Long userId;
    private LocalDateTime writtenDate;
    private LocalDateTime latestReviewDate;
    private Long reviewCount;
    private Boolean isYours;
}

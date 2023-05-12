package com.example.QuickThink.Card.Dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MyCardResponseDto {
    private Long id;
    private Boolean isYours;
    private String content;
    private HashSet<String> hashTags;
    private LocalDateTime writtenDate;
    private LocalDateTime latestReviewDate;
    private Long reviewCount;
}

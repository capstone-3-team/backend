package com.example.QuickThink.Card.Dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CardWriteRequestDto {
    private String content;
    private ArrayList<String> hashTags;
    private LocalDateTime latestReviewDate;
    private Long reviewCount;
    private String title;
    private String googleId;
}

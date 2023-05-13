package com.example.QuickThink.Card.Dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CardWriteRequestDto {
    private String title;

    private String content;

    private List<String> hashTags;

    private LocalDateTime writtenDate;

    private LocalDateTime latestReviewDate;

    private Long reviewCount;
}

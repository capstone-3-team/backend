package com.example.QuickThink.Card.Dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OtherCardResponseDto {
    private Long id;
    private Boolean isYours;
    private String content;
    private HashSet<String> hashTags;
    private LocalDateTime writtenDate;
}

package com.example.QuickThink.Card.Dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CardDtoRequest {
    private String title;
    private String content;
    private Set<String> hashTags;
    private LocalDateTime writtenDate;
}

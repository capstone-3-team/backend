package com.example.QuickThink.Card.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CardEditRequestDto {
    private String title;
    private String content;
    private List<String> hashTags;
    private LocalDateTime writtenDate;

}

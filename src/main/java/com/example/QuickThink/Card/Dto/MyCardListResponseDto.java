package com.example.QuickThink.Card.Dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MyCardListResponseDto {
    private Long size;
    private Boolean isYours;
    @Builder.Default
    private List<MyCardResponseDto> cards = new ArrayList<>();

    public void addCards(MyCardResponseDto myCardResponseDto) {
        cards.add(myCardResponseDto);
    }
}

package com.example.QuickThink.Card.Dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OtherCardListResponseDto {
    private Long size;
    private Boolean isYours;
    @Builder.Default
    private List<OtherCardResponseDto> cards = new ArrayList<>();

    public void addCards(OtherCardResponseDto otherCardResponseDto) {
        cards.add(otherCardResponseDto);
    }

}

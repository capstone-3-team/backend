package com.example.QuickThink.Card.Dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
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
    @NotEmpty
    private String title;
    @NotEmpty
    private String content;
    private List<String> hashTags;
    private LocalDateTime writtenDate;

}

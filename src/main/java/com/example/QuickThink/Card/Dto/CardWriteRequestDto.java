package com.example.QuickThink.Card.Dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CardWriteRequestDto {
    @NotEmpty
    private String title;
    @NotEmpty
    private String content;
    private List<String> hashTags;
    private LocalDateTime writtenDate;
    private LocalDateTime latestReviewDate;
    @PositiveOrZero
    private Long reviewCount;
}

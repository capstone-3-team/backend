package com.example.QuickThink.Google.Dto;

import lombok.*;

/**
 * 토큰 검증 결과를 보내줄 Dto
 */
@Getter
@Setter
public class TokenValidationDto {
    private Boolean isValid;
    public TokenValidationDto(Boolean value) {
        isValid = value;
    }
}

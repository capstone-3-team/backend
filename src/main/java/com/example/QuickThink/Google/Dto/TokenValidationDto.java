package com.example.QuickThink.Google.Dto;

import lombok.*;

@Getter
@Setter
public class TokenValidationDto {
    private Boolean isValid;
    public TokenValidationDto(Boolean value) {
        isValid = value;
    }
}

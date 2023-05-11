package com.example.QuickThink.Google.Dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponseDto {
    private String token;
    private String googleName;
    private String googleId;
    private String profilePicture;
    private Boolean firstLogin;
}

package com.example.QuickThink.Google.Dto;

import lombok.*;

/**
 * 로그인 완료 이후 프론트엔드에 전송할 Dto
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginDto {
    private String token;
    private String googleName;
    private String googleId;
    private String profilePicture;
}

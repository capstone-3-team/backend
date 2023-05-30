package com.example.QuickThink.Google.Dto;

import jakarta.validation.constraints.NotEmpty;
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
    @NotEmpty
    private String token;
    @NotEmpty
    private String googleName;
    @NotEmpty
    private String googleId;
    @NotEmpty
    private String profilePicture;
}

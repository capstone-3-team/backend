package com.example.QuickThink.Google.Dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String googleName;
    private String googleId;
    private String profilePicture;
    private String profileText;
}

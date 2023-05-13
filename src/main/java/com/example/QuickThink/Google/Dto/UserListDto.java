package com.example.QuickThink.Google.Dto;

import com.example.QuickThink.Google.Entity.UserEntity;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserListDto {
    List<UserDto> userList = new ArrayList<>();
    public void addUser(UserEntity user) {
        userList.add(
                UserDto
                        .builder()
                        .profilePicture(user.getProfilePicture())
                        .googleName(user.getGoogleName())
                        .googleId(user.getGoogleId())
                        .profileText(user.getProfileText())
                        .build()
        );
    }
}

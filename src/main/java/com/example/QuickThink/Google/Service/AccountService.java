package com.example.QuickThink.Google.Service;

import com.example.QuickThink.Card.Dto.HashtagsDto;
import com.example.QuickThink.Google.Dto.UserDto;
import com.example.QuickThink.Google.Dto.UserListDto;
import com.example.QuickThink.Google.Entity.UserEntity;
import com.example.QuickThink.Google.Exception.InvalidAuthorization;
import com.example.QuickThink.Google.Exception.InvalidToken;
import com.example.QuickThink.Google.Exception.NotFoundException;
import com.example.QuickThink.Google.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    UserRepository userRepository;

    LoginService loginService;

    public AccountService(UserRepository userRepository, LoginService loginService) {
        this.userRepository = userRepository;
        this.loginService = loginService;
    }

    public String getProfileText(String accessToken, String googleId) {
        loginService.getUserByToken(accessToken);
        UserEntity user = userRepository.findByGoogleId(googleId);
        if(user == null) throw new NotFoundException();
        return user.getProfileText();
    }

    @Transactional
    public void changeProfileText(String accessToken, String googleId, String profileText) {
        UserEntity tokenUser = loginService.getUserByToken(accessToken);
        UserEntity user = loginService.getUserByGoogleId(googleId);
        if(!tokenUser.getGoogleId().equals(user.getGoogleId())) {
            throw new InvalidAuthorization();
        }
        user.setProfileText(profileText);
    }

    public HashtagsDto getUserHashTags(String accessToken, String googleId) {
        loginService.getUserByToken(accessToken);
        UserEntity user = loginService.getUserByGoogleId(googleId);
        List<String> hashTags = new ArrayList<>(user.getHashTags().keySet());
        return new HashtagsDto(hashTags);
    }

    public UserListDto searchUsers(String accessToken, String search) {
        loginService.getUserByToken(accessToken);
        List<UserEntity> users = userRepository.findAllByGoogleNameContaining(search);
        UserListDto answers = new UserListDto();
        for(UserEntity user : users) {
            answers.addUser(user);
        }
        return answers;
    }

    public UserDto searchUser(String accessToken, String googleId) {
        loginService.getUserByToken(accessToken);
        UserEntity user = userRepository.findByGoogleId(googleId);
        if(user == null) {
            throw new NotFoundException();
        }
        return UserDto
                .builder()
                .googleName(user.getGoogleName())
                .profilePicture(user.getProfilePicture())
                .profileText(user.getProfileText())
                .googleId(user.getGoogleId())
                .build();
    }
}

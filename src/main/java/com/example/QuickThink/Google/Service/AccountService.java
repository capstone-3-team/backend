package com.example.QuickThink.Google.Service;

import com.example.QuickThink.Google.Entity.UserEntity;
import com.example.QuickThink.Google.Exception.InvalidAuthorization;
import com.example.QuickThink.Google.Exception.InvalidToken;
import com.example.QuickThink.Google.Exception.NotFoundException;
import com.example.QuickThink.Google.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

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
        if(tokenUser.getId() != user.getId()) {
            throw new InvalidAuthorization();
        }
        user.setProfileText(profileText);
    }
}

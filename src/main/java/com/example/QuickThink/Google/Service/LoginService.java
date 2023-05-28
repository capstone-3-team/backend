package com.example.QuickThink.Google.Service;


import com.example.QuickThink.Google.Dto.LoginDto;
import com.example.QuickThink.Google.Entity.UserEntity;
import com.example.QuickThink.Google.Exception.InvalidToken;
import com.example.QuickThink.Google.Exception.NotFoundException;
import com.example.QuickThink.Google.Repository.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@Slf4j
public class LoginService {

    private final Environment env;
    private final RestTemplate restTemplate = new RestTemplate();
    private UserRepository userRepository;
    private String clientId;
    private String clientSecret;
    private String redirectUri;
    private String tokenUri;

    public LoginService(Environment env, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.env = env;
        clientId = env.getProperty("google.client-id");
        clientSecret = env.getProperty("google.client-secret");
        redirectUri = env.getProperty("google.redirect-uri");
        tokenUri = env.getProperty("google.token-uri");
    }

    /**
     * 구글 로그인 주소를 만들어 리턴합니다.
     * @return
     */
    public String googleRedirect() {
        Map<String, Object> params = new HashMap<>();
        params.put("scope", "https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email%20https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.profile");
        params.put("response_type", "code");
        params.put("client_id", clientId);
        params.put("redirect_uri", redirectUri);

        log.info("redirect into {}", redirectUri);

        String parameterString = params.entrySet().stream()
                .map(x -> x.getKey() + "=" + x.getValue())
                .collect(Collectors.joining("&"));

        return "https://accounts.google.com/o/oauth2/v2/auth" + "?" + parameterString;
    }

    public Boolean registration(LoginDto loginDto) {

        String googleId = loginDto.getGoogleId();
        String googleName = loginDto.getGoogleName();
        String accessToken = loginDto.getToken();
        String profilePicture = loginDto.getProfilePicture();

        // 유저를 가져온 후 있으면 갱신, 없으면 추가합니다.
        UserEntity user = userRepository.findByGoogleId(googleId);
        if(user == null) {
            UserEntity newUser = UserEntity
                    .builder()
                    .googleId(googleId)
                    .googleName(googleName)
                    .accessToken(accessToken)
                    .profilePicture(profilePicture)
                    .build();
            userRepository.save(newUser);
            return true;
        } else {
            user.setGoogleName(googleName);
            user.setAccessToken(accessToken);
            user.setGoogleId(googleId);
            user.setProfilePicture(profilePicture);
            userRepository.save(user);
            log.info(accessToken);
            return false;
        }
    }

    /**
     * ID 와 토큰을 통해 토큰의 유효성을 검증합니다.
     * @param accessToken
     * @param googleId
     * @return
     */
    @Transactional
    public Boolean tokenValidation(String accessToken, String googleId) {
        if(userRepository.findByGoogleIdAndAccessToken(googleId, accessToken) == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 해당 토큰을 가진 유저를 가져옵니다.
     * 만약 없다면 401 UNAUTHORIZED 에러를 리턴합니다.
     * @param accessToken
     * @return
     */
    public UserEntity getUserByToken(String accessToken) {
        UserEntity user = userRepository.findByAccessToken(accessToken);
        if(user == null) {
            throw new InvalidToken();
        } else {
            return user;
        }
    }

    /**
     * 해당 구글아이디를 가진 유저를 가져옵니다.
     * 만약 없다면 404 NOT_FOUND 에러를 리턴합니다.
     * @param googleId
     * @return
     */
    public UserEntity getUserByGoogleId(String googleId) {
        UserEntity user = userRepository.findByGoogleId(googleId);
        if(user == null) {
            throw new NotFoundException();
        } else {
            return user;
        }
    }
}

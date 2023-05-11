package com.example.QuickThink.oauth.service;

import com.example.QuickThink.oauth.domain.User;
import com.example.QuickThink.oauth.dto.GoogleInfResponse;
import com.example.QuickThink.oauth.dto.GoogleRequest;
import com.example.QuickThink.oauth.dto.GoogleResponse;
import com.example.QuickThink.oauth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class LoginService {
    @Value("${google.client.id}")
    private String googleClientId;

    @Value("${google.client.pw}")
    private String googleClientPw;

    @Autowired
    UserRepository userRepository;

    public String loginUrl() {
        String reqUrl = "https://accounts.google.com/o/oauth2/v2/auth?client_id=" + googleClientId
                + "&redirect_uri=http://localhost:8080/api/v1/oauth2/google&response_type=code&scope=email%20profile%20openid&access_type=offline";
        return reqUrl;
    }

    public String getGoogleEmail(String authCode){
        RestTemplate restTemplate = new RestTemplate();
        GoogleRequest googleOAuthRequestParam = GoogleRequest
                .builder()
                .clientId(googleClientId)
                .clientSecret(googleClientPw)
                .code(authCode)
                .redirectUri("http://localhost:8080/api/v1/oauth2/google")
                .grantType("authorization_code").build();
        ResponseEntity<GoogleResponse> resultEntity = restTemplate.postForEntity("https://oauth2.googleapis.com/token",
                googleOAuthRequestParam, GoogleResponse.class);
        String jwtToken=resultEntity.getBody().getId_token();
        Map<String, String> map=new HashMap<>();
        map.put("id_token",jwtToken);
        ResponseEntity<GoogleInfResponse> resultEntity2 = restTemplate.postForEntity("https://oauth2.googleapis.com/tokeninfo",
                map, GoogleInfResponse.class);
        String googleEmail=resultEntity2.getBody().getEmail();
        String googleName= resultEntity2.getBody().getName();
        Optional<User> userResult = userRepository.findByEmail(googleEmail);
        if(!userResult.isPresent()) {
            // user email and user name send to client!!!
            User newUser = User.builder()
                    .userEmail(googleEmail)
                    .userName(googleName)
                    .build();
            userRepository.save(newUser);
            return "Login Successfully!";
        }

        return "Already exists!";
    }
/*
    public String loginGoogle(String authCode) {
        String googleEmail = getGoogleEmail(authCode);
        System.out.println("***************************** : " + googleEmail);
        Optional<User> userResult = userRepository.findByEmail(googleEmail);
        if(!userResult.isPresent()) {
            // user email and user name send to client!!!
            User newUser = User.builder()
                    .userEmail(googleEmail)
                    .userName("받아오셈")
                    .build();
            userRepository.save(newUser);
            return "Login Successfully!";
        }

        return "Already exists! " + userResult.toString();
    }*/
}


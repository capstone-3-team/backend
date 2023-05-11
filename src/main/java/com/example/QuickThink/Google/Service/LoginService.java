package com.example.QuickThink.Google.Service;


import com.example.QuickThink.Google.Dto.LoginResponseDto;
import com.example.QuickThink.Google.Entity.UserEntity;
import com.example.QuickThink.Google.Repository.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.transaction.Transactional;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


@Service
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

        String parameterString = params.entrySet().stream()
                .map(x -> x.getKey() + "=" + x.getValue())
                .collect(Collectors.joining("&"));

        return "https://accounts.google.com/o/oauth2/v2/auth" + "?" + parameterString;
    }

    /**
     * 토큰과 함께 유저 데이터를 받아와 회원가입하고 중요한 데이터는 리턴하도록 합니다.
     * @param authorizationCode
     * @return
     */
    public LoginResponseDto getDto(String authorizationCode) {
        // 토큰을 받아오기 위한 패러미터를 준비합니다.
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", authorizationCode);
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", redirectUri);
        params.add("grant_type", "authorization_code");

        // 토큰을 받아옵니다.
        HttpHeaders tokenHeaders = new HttpHeaders();
        tokenHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity tokenEntity = new HttpEntity(params, tokenHeaders);
        ResponseEntity<JsonNode> tokenResponseNode = restTemplate.exchange(tokenUri, HttpMethod.POST, tokenEntity, JsonNode.class);
        JsonNode accessTokenNode = tokenResponseNode.getBody();

        // 받아온 토큰들을 통해 유저의 정보또한 받아옵니다.
        String resourceUri = env.getProperty("google.resource-uri");
        HttpHeaders userHeaders = new HttpHeaders();
        userHeaders.set("Authorization", "Bearer " + accessTokenNode.get("access_token").asText());
        HttpEntity userEntity = new HttpEntity(userHeaders);
        ResponseEntity<JsonNode> UserResponseNode = restTemplate.exchange(resourceUri, HttpMethod.GET, userEntity, JsonNode.class);
        JsonNode userDataNode = UserResponseNode.getBody();

        String accessToken = accessTokenNode.get("access_token").asText();
        String googleName = userDataNode.get("name").asText();
        String googleId = userDataNode.get("id").asText();
        String profilePicture = userDataNode.get("picture").asText();

        Boolean first = registration(googleName, googleId, profilePicture, accessToken);

        // 프론트엔드 단에서 사용할 Dto 입니다.
        LoginResponseDto loginResponseDto = LoginResponseDto
                .builder()
                .token(accessToken)
                .googleName(googleName)
                .googleId(googleId)
                .profilePicture(profilePicture)
                .firstLogin(first)
                .build();

        return loginResponseDto;
    }

    /**
     * 이전에 로그인 한 적이 있는지 여부를 파악해 새로운 유저로 DB에 넣을지, 유저 데이터만 최신으로 바꾸어 줄지를 결정합니다.
     * @param googleName
     * @param googleId
     * @param profilePicture
     * @param accessToken
     * @return
     */
    @Transactional
    public Boolean registration(String googleName, String googleId, String profilePicture, String accessToken) {
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
}

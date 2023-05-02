package com.example.QuickThink.service;

import lombok.RequiredArgsConstructor;
import com.example.QuickThink.helper.constants.SocialLoginType;
import com.example.QuickThink.service.social.SocialOauth;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OauthService {
    private final List<SocialOauth> socialOuathList;
    private final HttpServletResponse response;

    public void request(SocialLoginType socialLoginType){
        SocialOauth socialOauth = this.findSocialOauthByType(socialLoginType);
        String redirectURL = socialOauth.getOauthRedirectURL();
        try {
            response.sendRedirect(redirectURL);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String requestAccessToken(SocialLoginType socialLoginType, String code){
        SocialOauth socialOauth = this.findSocialOauthByType(socialLoginType);
        return socialOauth.requestAccessToken(code);
        //return socialOauth.requestAccessTokenUsingURL(code);
    }
    private SocialOauth findSocialOauthByType(SocialLoginType socialLoginType) {
        return socialOuathList.stream()
                .filter(x -> x.type() == socialLoginType)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("알 수 없는 SocialLoginType 입니다."));
    }

}

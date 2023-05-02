package com.example.QuickThink.service.social;

import com.example.QuickThink.helper.constants.SocialLoginType;

public interface SocialOauth {
    String getOauthRedirectURL();
    String requestAccessToken(String code);
    //String requestAccessTokenUsingURL(String code);

    default SocialLoginType type(){
        if (this instanceof GoogleOauth){
            return SocialLoginType.GOOGLE;
        }
        else{
            return null;
        }
    }
}

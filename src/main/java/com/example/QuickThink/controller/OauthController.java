package com.example.QuickThink.controller;

import com.example.QuickThink.helper.constants.SocialLoginType;
import com.example.QuickThink.service.OauthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
@RestController
@CrossOrigin
@RequestMapping(value = "/auth")
@Slf4j//이거 해야지 log쓰기 가능
@RequiredArgsConstructor//이거 해줘야지 private final 가능
public class OauthController {
    private final OauthService oauthService;

    @GetMapping(value = "/{socialLoginType}")
    public void socialLoginType(
            @PathVariable(name = "socialLoginType") SocialLoginType socialLoginType) {
        log.info(">> 사용자로부터 SNS 로그인 요청을 받음 :: {} Social Login", socialLoginType);
        oauthService.request(socialLoginType);
    }

    @GetMapping(value = "/{socialLoginType}/callback")
    public String callback(
            @PathVariable(name = "socialLoginType") SocialLoginType socialLoginType,
            @RequestParam(name = "code") String code) {
        log.info(">> 소셜 로그인 API 서버로부터 받은 code :: {}", code);
        return oauthService.requestAccessToken(socialLoginType, code);
    }

}

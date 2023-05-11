package com.example.QuickThink.oauth.controller;

import com.example.QuickThink.oauth.domain.User;
import com.example.QuickThink.oauth.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
public class LoginController {
    @Autowired
    LoginService loginService;

    @RequestMapping(value="/api/v1/oauth2/google", method = RequestMethod.POST)
    public String loginUrlGoogle(){
        return loginService.loginUrl();
    }

    // Oauth2를 통해서 사용자의 이름과 이메일 받음
    @RequestMapping(value="/api/v1/oauth2/google", method = RequestMethod.GET)
    public String loginGoogle(@RequestParam(value = "code") String authCode){
        System.out.println("**************************************************************s");
        return loginService.getGoogleEmail(authCode);
    }

    @RequestMapping(value="/signUp", method = RequestMethod.POST)
    public String signUp(@RequestBody User user){
        return user.toString();
    }
}


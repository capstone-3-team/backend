package com.example.QuickThink.Google.Controller;

import com.example.QuickThink.Card.Dto.HashtagsDto;
import com.example.QuickThink.Google.Dto.*;
import com.example.QuickThink.Google.Entity.UserEntity;
import com.example.QuickThink.Google.Exception.InvalidRedirect;
import com.example.QuickThink.Google.Service.AccountService;
import com.example.QuickThink.Google.Service.LoginService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Slf4j
public class GoogleController {
    LoginService loginService;
    AccountService accountService;

    public GoogleController(LoginService loginService, AccountService accountService) {
        this.loginService = loginService;
        this.accountService = accountService;
    }

    //리다이렉트
    @GetMapping("/google")
    public void googleUrl(HttpServletResponse res) {
        String url = loginService.googleRedirect();
        try {
            res.sendRedirect(url);
        } catch (Exception e) {
            throw new InvalidRedirect();
        }
    }

    // 콜백
    @PostMapping("/auth")
    public ResponseEntity<HttpStatus> googleLogin(@RequestBody LoginDto loginDto) {
        loginService.registration(loginDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/info")
    ResponseEntity<LoginDto> getTokenInfo(@RequestHeader("accessToken") String accessToken) {
        UserEntity user = loginService.getUserByToken(accessToken);
        return new ResponseEntity<>(LoginDto.builder().token(accessToken).googleId(user.getGoogleId()).googleName(user.getGoogleName()).profilePicture(user.getProfilePicture()).build(), HttpStatus.OK);
    }

    // 토큰 검증
    @GetMapping("/token")
    public ResponseEntity<TokenValidationDto> tokenValidation(@RequestParam String accessToken, @RequestParam String googleId) {
        Boolean validationResult = loginService.tokenValidation(accessToken, googleId);
        return new ResponseEntity<>(new TokenValidationDto(validationResult), HttpStatus.OK);
    }

    // 프로필 텍스트 내용 가져오기
    @GetMapping("/profile")
    public ResponseEntity<ProfileDto> getProfileText(@RequestHeader("accessToken") String accessToken, @RequestParam String googleId) {
        String profileText = accountService.getProfileText(accessToken, googleId);
        return new ResponseEntity<>(new ProfileDto(profileText), HttpStatus.OK);
    }

    // 프로필 텍스트 내용 수정하기
    @PostMapping("/profile")
    public ResponseEntity<ProfileDto> changeProfileText(@RequestHeader("accessToken") String accessToken, @RequestParam String googleId, @RequestBody ProfileDto profile) {
        accountService.changeProfileText(accessToken, googleId, profile.getText());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/hashtags")
    public ResponseEntity<HashtagsDto> getUserHashTags(@RequestHeader("accessToken") String accessToken, @RequestParam String googleId) {
        HashtagsDto hashTags = accountService.getUserHashTags(accessToken, googleId);
        return new ResponseEntity<>(hashTags, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<UserListDto> searchUsers(@RequestHeader("accessToken") String accessToken, @RequestParam String searchName) {
        UserListDto userListDto = accountService.searchUsers(accessToken, searchName);
        return new ResponseEntity<>(userListDto, HttpStatus.OK);
    }

    @GetMapping("/person")
    public ResponseEntity<UserDto> searchUser(@RequestHeader("accessToken") String accessToken, @RequestParam String googleId) {
        UserDto userDto = accountService.searchUser(accessToken, googleId);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
}

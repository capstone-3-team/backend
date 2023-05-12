package com.example.QuickThink.Google.Controller;

import com.example.QuickThink.Google.Dto.LoginResponseDto;
import com.example.QuickThink.Google.Dto.TokenValidationDto;
import com.example.QuickThink.Google.Exception.InvalidRedirect;
import com.example.QuickThink.Google.Service.AccountService;
import com.example.QuickThink.Google.Service.LoginService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
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
    @GetMapping("/auth")
    public ResponseEntity<LoginResponseDto> googleLogin(@RequestParam String code) {
        LoginResponseDto loginResponseDto = loginService.getDto(code);
        return new ResponseEntity<>(loginResponseDto, HttpStatus.OK);
    }

    // 토큰 검증
    @GetMapping("/token")
    public ResponseEntity<TokenValidationDto> tokenValidation(@RequestParam String accessToken, @RequestParam String googleId) {
        Boolean validationResult = loginService.tokenValidation(accessToken, googleId);
        return new ResponseEntity<>(new TokenValidationDto(validationResult), HttpStatus.OK);
    }

    // 프로필 텍스트 내용 가져오기
    @GetMapping("/profile")
    public ResponseEntity<String> getProfileText(@RequestHeader("accessToken") String accessToken, @RequestParam String googleId) {
        String profileText = accountService.getProfileText(accessToken, googleId);
        return new ResponseEntity<>(profileText, HttpStatus.OK);
    }

    // 프로필 텍스트 내용 수정하기
    @PostMapping("/profile")
    public ResponseEntity<HttpStatus> changeProfileText(@RequestHeader("accessToken") String accessToken, @RequestParam String googleId, @RequestBody String profileText) {
        accountService.changeProfileText(accessToken, googleId, profileText);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

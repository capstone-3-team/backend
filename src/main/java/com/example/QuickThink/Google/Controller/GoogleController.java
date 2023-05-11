package com.example.QuickThink.Google.Controller;

import com.example.QuickThink.Google.Dto.LoginResponseDto;
import com.example.QuickThink.Google.Dto.TokenValidationDto;
import com.example.QuickThink.Google.Exception.InvalidRedirect;
import com.example.QuickThink.Google.Service.LoginService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class GoogleController {
    LoginService loginService;

    public GoogleController(LoginService loginService) {
        this.loginService = loginService;
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
}

package com.example.QuickThink.Google.Exception;

public class InvalidToken extends RuntimeException {
    public InvalidToken() {
        super("토큰이 유효하지 않습니다.");
    }
}

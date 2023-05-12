package com.example.QuickThink.Google.Exception;

public class InvalidRedirect extends RuntimeException {
    public InvalidRedirect() {
        super("구글 로그인 리다이렉트에 실패했습니다.");
    }
}

package com.example.QuickThink.Google.Exception;

public class InvalidAuthorization extends RuntimeException {
    public InvalidAuthorization() {
        super("권한이 없습니다.");
    }
}

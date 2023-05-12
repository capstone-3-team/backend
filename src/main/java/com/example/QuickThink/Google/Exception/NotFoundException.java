package com.example.QuickThink.Google.Exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException() {
        super("요청한 데이터를 찾을수 없습니다.");
    }
}

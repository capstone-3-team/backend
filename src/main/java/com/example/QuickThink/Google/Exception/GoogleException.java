package com.example.QuickThink.Google.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 로그인 관련된 Exception들을 Handling
 */
@ControllerAdvice
public class GoogleException {
    @ExceptionHandler(InvalidRedirect.class)
    public ResponseEntity<String> redirectException(InvalidRedirect err) {
        return new ResponseEntity<>(err.getMessage(), HttpStatus.BAD_REQUEST);
    }
}

package com.promotion.amongapi.advice.handler;

import com.promotion.amongapi.advice.ErrorResopnse;
import com.promotion.amongapi.advice.ErrorStatus;
import com.promotion.amongapi.advice.exception.AuthorizeKeyNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice("com.promotion.amongapi.controller")
public class AuthorizeKeyNotFoundHandler {
    @ExceptionHandler(AuthorizeKeyNotFoundException.class)
    public ResponseEntity<ErrorResopnse> handleException(AuthorizeKeyNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResopnse(exception.getMessage(), ErrorStatus.AUTHORIZE_KEY_NOT_FOUND));
    }
}

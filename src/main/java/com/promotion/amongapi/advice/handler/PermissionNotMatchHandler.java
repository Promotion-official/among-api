package com.promotion.amongapi.advice.handler;

import com.promotion.amongapi.advice.ErrorResopnse;
import com.promotion.amongapi.advice.ErrorStatus;
import com.promotion.amongapi.advice.exception.PermissionNotMatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice("com.promotion.amongapi.controller")
public class PermissionNotMatchHandler {
    @ExceptionHandler(PermissionNotMatchException.class)
    public ResponseEntity<ErrorResopnse> handleException(PermissionNotMatchException exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResopnse(exception.getMessage(), ErrorStatus.PERMISSION_NOT_MATCH));
    }
}

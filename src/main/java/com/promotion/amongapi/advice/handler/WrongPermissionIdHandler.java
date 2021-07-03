package com.promotion.amongapi.advice.handler;

import com.promotion.amongapi.advice.ErrorResopnse;
import com.promotion.amongapi.advice.ErrorStatus;
import com.promotion.amongapi.advice.exception.WrongPermissionIdxException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice("com.promotion.amongapi.controller")
public class WrongPermissionIdHandler {
    @ExceptionHandler(WrongPermissionIdxException.class)
    public ResponseEntity<ErrorResopnse> handleException(WrongPermissionIdxException exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResopnse(exception.getMessage(), ErrorStatus.WRONG_PERMISSION));
    }
}

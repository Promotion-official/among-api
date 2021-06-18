package com.promotion.amongapi.advice.handler;

import com.promotion.amongapi.advice.ErrorResopnse;
import com.promotion.amongapi.advice.ErrorStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;

@RestControllerAdvice("com.promotion.amongapi.controller")
public class EntityNotFountHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResopnse> handleException(EntityNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResopnse(exception.getMessage(), ErrorStatus.ENTITY_NOT_FOUND));
    }
}

package com.promotion.amongapi.advice.handler;

import com.promotion.amongapi.advice.ErrorResopnse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;

public class EntityNotFountHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResopnse> handleException(EntityNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResopnse(exception.getMessage(), HttpStatus.BAD_REQUEST));
    }
}

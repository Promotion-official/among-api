package com.promotion.amongapi.advice;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode
@AllArgsConstructor
public class ErrorResopnse {
    private final String message;
    private final HttpStatus status;
}

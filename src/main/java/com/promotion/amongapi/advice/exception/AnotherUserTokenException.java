package com.promotion.amongapi.advice.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class AnotherUserTokenException extends RuntimeException {
    private final String email;
}

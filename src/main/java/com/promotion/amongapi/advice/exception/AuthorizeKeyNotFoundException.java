package com.promotion.amongapi.advice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class AuthorizeKeyNotFoundException extends RuntimeException {
    @Getter
    String authorizeKey;
}

package com.promotion.amongapi.advice.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EntityAlreadyExistException extends RuntimeException {
    @Getter
    private final Object entity;
}

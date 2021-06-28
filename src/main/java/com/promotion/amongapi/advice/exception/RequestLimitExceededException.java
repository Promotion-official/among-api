package com.promotion.amongapi.advice.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RequestLimitExceededException extends RuntimeException {
    private final int requestLimit;
    private final int requestCount;
}

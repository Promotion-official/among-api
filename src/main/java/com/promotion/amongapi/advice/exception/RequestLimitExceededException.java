package com.promotion.amongapi.advice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RequestLimitExceededException extends RuntimeException {
    @Getter
    private final int requestLimit;
    @Getter
    private final int requestCount;
}

package com.promotion.amongapi.advice;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
public class ErrorResopnse {
    private final String message;
    private final ErrorStatus status;

    public ErrorResopnse(String message, int status) {
        this.message = message;
        this.status = ErrorStatus.of(status);
    }
}

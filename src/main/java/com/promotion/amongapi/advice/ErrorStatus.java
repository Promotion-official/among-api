package com.promotion.amongapi.advice;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

@RequiredArgsConstructor
public enum ErrorStatus {
    ENTITY_NOT_FOUND(4000),
    AUTHORIZE_KEY_NOT_FOUND(4001),
    PERMISSION_NOT_MATCH(4002),
    REQUEST_LIMIT_EXCEEDED(4003),
    UNKNOWN_STRATEGY(4004),
    WRONG_CONDITION_TYPE(4005),
    WRONG_PERMISSION(4006),
    ENTITY_ALREADY_EXIST(4007),
    WRONG_TOKEN_DATA(4008);

    @Getter
    private final int code;

    public static ErrorStatus of(int code) {
        AtomicReference<ErrorStatus> status = new AtomicReference<>();

        Arrays.stream(values()).forEach(errorStatus -> {
            if(errorStatus.code == code) status.set(errorStatus);
        });

        return status.get();
    }
}

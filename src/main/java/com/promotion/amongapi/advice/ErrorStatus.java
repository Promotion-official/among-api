package com.promotion.amongapi.advice;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

@AllArgsConstructor
public enum ErrorStatus {
    ENTITY_NOT_FOUND(4000);

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

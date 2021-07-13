package com.promotion.amongapi.advice.exception;

import com.promotion.amongapi.domain.Permission;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PermissionDeniedPageAccessException extends RuntimeException {
    private final Permission requestPermission;
    private final Permission[] usablePermission;
}

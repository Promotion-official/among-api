package com.promotion.amongapi.domain;

import com.promotion.amongapi.advice.exception.WrongPermissionIdxException;

public enum Permission {
    DEVELOPER, PRODUCT, OPERATOR, ADMINISTRATOR;

    public static Permission of(String permission) {
        for (Permission value : values()) {
            if(permission.equals(value.name()))
                return value;
        }
        throw new WrongPermissionIdxException();
    }


    @Override
    public String toString() {
        return this.name();
    }
}
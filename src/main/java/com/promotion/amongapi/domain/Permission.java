package com.promotion.amongapi.domain;

import com.promotion.amongapi.advice.exception.WrongPermissionIdxException;

public enum Permission {
    DEVELOPER, PRODUCT, OPERATOR, ADMINISTRATOR;

    public static Permission of(int permission) {
        if(values().length <= permission) throw new WrongPermissionIdxException();
        return values()[permission];
    }
}

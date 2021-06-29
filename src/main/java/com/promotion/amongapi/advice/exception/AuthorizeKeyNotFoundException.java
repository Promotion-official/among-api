package com.promotion.amongapi.advice.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.EntityNotFoundException;

@NoArgsConstructor
public class AuthorizeKeyNotFoundException extends RuntimeException {
    public AuthorizeKeyNotFoundException(EntityNotFoundException e) {
        super(e);
    }
}

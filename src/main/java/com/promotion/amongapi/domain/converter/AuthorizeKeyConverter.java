package com.promotion.amongapi.domain.converter;

import com.promotion.amongapi.advice.ErrorResopnse;
import com.promotion.amongapi.advice.ErrorStatus;
import com.promotion.amongapi.advice.exception.AuthorizeKeyNotFoundException;
import com.promotion.amongapi.domain.Permission;
import com.promotion.amongapi.domain.dto.AuthorizeKeyDto;
import com.promotion.amongapi.domain.entity.AuthorizeKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;

@Component @Slf4j
public class AuthorizeKeyConverter implements DtoConverter<AuthorizeKey, AuthorizeKeyDto> {
    @Override
    public AuthorizeKey convertDtoToEntity(AuthorizeKeyDto dto) {
        return new AuthorizeKey(dto.getAuthorizeKey(), dto.getPerm().ordinal());
    }

    @Override
    public AuthorizeKeyDto convertEntityToDto(AuthorizeKey entity) {
        return AuthorizeKeyDto.builder()
                .authorizeKey(entity.getAuthorizeKey())
                .perm(Permission.of(entity.getPermission()))
                .build();
    }
}

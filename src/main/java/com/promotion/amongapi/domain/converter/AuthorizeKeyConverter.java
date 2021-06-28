package com.promotion.amongapi.domain.converter;

import com.promotion.amongapi.domain.Permission;
import com.promotion.amongapi.domain.dto.AuthorizeKeyDto;
import com.promotion.amongapi.domain.entity.AuthorizeKey;
import org.springframework.stereotype.Component;

@Component
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

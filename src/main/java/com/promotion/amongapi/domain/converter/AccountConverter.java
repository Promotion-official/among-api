package com.promotion.amongapi.domain.converter;

import com.promotion.amongapi.domain.dto.AccountDto;
import com.promotion.amongapi.domain.entity.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountConverter implements DtoConverter<Account, AccountDto> {
    @Override
    public Account convertDtoToEntity(AccountDto dto) {
        return new Account(
                dto.getEmail(),
                dto.getName(),
                dto.getGeneration(),
                dto.getClazz(),
                dto.getNumber());
    }

    @Override
    public AccountDto convertEntityToDto(Account entity) {
        return AccountDto.builder()
                .email(entity.getEmail())
                .name(entity.getName())
                .generation(entity.getGeneration())
                .clazz(entity.getClazz())
                .number(entity.getNumber())
                .build();
    }
}

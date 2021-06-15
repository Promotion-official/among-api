package com.promotion.amongapi.domain.converter;

public interface DtoConverter <E, D>{
    E convertDtoToEntity(D dto);
    D convertEntityToDto(E entity);
}

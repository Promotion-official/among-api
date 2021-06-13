package com.promotion.amongapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.constraints.NotBlank;

@JsonInclude(JsonInclude.Include.NON_NULL)

@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode
@Getter @Setter
@ToString
public class AccountDto {
    private String name;
    @NotBlank
    private final String email;
    private int clazz;
    private int number;
    private int generation;
}

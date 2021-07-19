package com.promotion.amongapi.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.constraints.NotBlank;

@JsonInclude(JsonInclude.Include.NON_NULL)

@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter @Setter
@ToString
public class AccountDto {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    private String name;
    private int clazz;
    private int number;
    private int generation;
}

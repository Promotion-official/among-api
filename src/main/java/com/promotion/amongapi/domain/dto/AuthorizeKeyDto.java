package com.promotion.amongapi.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.promotion.amongapi.domain.Permission;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)

@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@ToString
public class AuthorizeKeyDto {
    private String authorizeKey;
    private Permission perm;
}

package com.promotion.amongapi.domain.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@ToString
public class EncodedAuthTokenDto {
    String email;
    String clientId;
}

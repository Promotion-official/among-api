package com.promotion.amongapi.domain.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor

@Entity @Table(name = "authorize_key")
public class AuthorizeKey {
    @Id @Column(name = "authorize_key")
    private String authorizeKey;
    @Column(name = "permission")
    private int permission;
}

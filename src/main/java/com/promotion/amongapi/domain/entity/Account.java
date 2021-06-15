package com.promotion.amongapi.domain.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class Account {
    @Id
    private String email;
    private String name;
    private int generation;
    private int clazz;
    private int number;
}

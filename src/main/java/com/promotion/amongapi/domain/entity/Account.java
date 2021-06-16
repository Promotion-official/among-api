package com.promotion.amongapi.domain.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Max;

@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class Account {
    @Id @Length(max = 50)
    private String email;
    @Length(max = 5)
    private String name;
    @Max(100)
    private int generation;
    @Max(4)
    private int clazz;
    @Max(20)
    private int number;
}

package com.promotion.amongapi.controller;

import com.promotion.amongapi.logic.AccountCountStrategy;
import com.promotion.amongapi.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CountController {
    AccountService service;

    public int countName(String name) {
        return service.count(AccountCountStrategy.NAME, name);
    }

    public int countClazz(int clazz) {
        return service.count(AccountCountStrategy.CLAZZ, clazz);
    }

    public int countGrade(int grade) {
        return service.count(AccountCountStrategy.GRADE, grade);
    }

    public int countGeneration(int generation) {
        return service.count(AccountCountStrategy.GENERATION, generation);
    }
}

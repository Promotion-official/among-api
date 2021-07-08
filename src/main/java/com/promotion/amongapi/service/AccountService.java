package com.promotion.amongapi.service;

import com.promotion.amongapi.domain.dto.AccountDto;
import com.promotion.amongapi.domain.entity.Account;
import com.promotion.amongapi.advice.exception.UnknownStrategyException;
import com.promotion.amongapi.advice.exception.WrongConditionTypeException;
import com.promotion.amongapi.logic.AccountCountStrategy;
import com.promotion.amongapi.repository.AccountRepository;
import com.promotion.amongapi.domain.converter.AccountConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Max;
import java.util.Calendar;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class AccountService {
    private final AccountRepository repository;
    private final AccountConverter converter;

    public AccountService(AccountRepository repository) {
        this.repository = repository;
        converter = new AccountConverter();
    }

    public void add(AccountDto account) {
        repository.save(converter.convertDtoToEntity(account));
    }

    public AccountDto get(String email) {
        Account entity = new Account();
        try {
        entity = repository.getById(email);
        } catch (DataAccessException e) {
            System.out.println("테스트 성공 : " + e.getMessage());
        }
        return converter.convertEntityToDto(entity);
    }

    public void updateStudentId(String email, int studentId) {
        int[] studentIdData= translateStudentIdToAccountDto(studentId);
        AccountDto dto = get(email);

        dto.setGeneration(studentIdData[TranslateArray.GEN.index]);
        dto.setClazz(studentIdData[TranslateArray.CLAZZ.index]);
        dto.setNumber(studentIdData[TranslateArray.NUMBER.index]);

        repository.save(converter.convertDtoToEntity(dto));
    }

    //조건에 따라 학생 수 카운트
    public int count(AccountCountStrategy strategy, Object condition) {
        AtomicInteger count = new AtomicInteger();
        Optional.ofNullable(strategy).ifPresentOrElse((countStrategy)->{
            try {
                switch (countStrategy) {
                    case NAME:
                        count.set(repository.countByName((String) condition));
                        break;
                    case GENERATION:
                        count.set(repository.countByGeneration((int) condition));
                        break;
                    case GRADE:
                        int generation = translateStudentIdToAccountDto((int) condition * 1000)[TranslateArray.GEN.index]; //학번 생성 로직을 통해, grade 로 generation 을 구한다.
                        count.set(repository.countByGeneration(generation));
                        break;
                    case CLAZZ:
                        count.set(repository.countByClazz((int) condition));
                        break;
                    default:
                        throw new UnknownStrategyException();
                }
            } catch (ClassCastException e) {
                throw new WrongConditionTypeException();
            }
        }, ()->{
            throw new UnknownStrategyException();
        });
        return count.get();
    }

    //학번으로 계정 가져오기
    private int[] translateStudentIdToAccountDto(@Max(9999) int studentId) {
        Calendar cal = Calendar.getInstance();
        int currentYear = cal.get(Calendar.YEAR);
        int grade = studentId / 1000;
        int gen = currentYear - 2015 - grade;
        int clazz = (studentId % 1000) / 100;
        int number = studentId % 100;

        return new int[]{gen, grade, clazz, number};
    }

    @RequiredArgsConstructor
    private enum TranslateArray {
        GEN(0), GRADE(1), CLAZZ(2), NUMBER(3);

        private final int index;
    }
}

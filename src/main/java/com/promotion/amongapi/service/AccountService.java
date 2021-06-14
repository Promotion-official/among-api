package com.promotion.amongapi.service;

import com.promotion.amongapi.dto.AccountDto;
import com.promotion.amongapi.repository.AccountRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Max;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service @Component
public class AccountService {
    private final List<AccountDto> accountList;
    private final AccountRepository repository;

    public AccountService(AccountRepository repository) {
        accountList = new ArrayList<>();
        this.repository = repository;
    }

    public void add(AccountDto account) {
        repository.save(account);
    }

    public AccountDto get(String email) {
        return repository.getAccountDtoByEmail(email);
    }

    public void updateStudentId(String email, int studentId) {
        int[] studentIdData= translateStudentIdToAccountDto(studentId);
        AccountDto dto = get(email);

        dto.setGeneration(studentIdData[TranslateArray.GEN.index]);
        dto.setClazz(studentIdData[TranslateArray.CLAZZ.index]);
        dto.setNumber(studentIdData[TranslateArray.NUMBER.index]);

        repository.save(dto);
    }

    private int[] translateStudentIdToAccountDto(@Max(9999) int studentId) {
        Calendar cal = Calendar.getInstance();
        int currentYear = cal.get(Calendar.YEAR);
        int grade = studentId / 1000;
        int gen = currentYear - 2015 - grade;
        int clazz = (studentId % 1000) / 100;
        int number = studentId % 100;

        return new int[]{gen, grade, clazz, number};
    }

    @AllArgsConstructor
    private enum TranslateArray {
        GEN(0), GRADE(1), CLAZZ(2), NUMBER(3);

        private final int index;
    }
}

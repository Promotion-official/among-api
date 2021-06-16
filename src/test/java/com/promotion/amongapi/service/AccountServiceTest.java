package com.promotion.amongapi.service;

import com.promotion.amongapi.domain.converter.AccountDtoConverter;
import com.promotion.amongapi.domain.dto.AccountDto;
import com.promotion.amongapi.domain.entity.Account;
import com.promotion.amongapi.advice.exception.UnknownStrategyException;
import com.promotion.amongapi.advice.exception.WrongConditionTypeException;
import com.promotion.amongapi.logic.AccountCountStrategy;
import com.promotion.amongapi.repository.AccountRepository;
import com.thedeanda.lorem.LoremIpsum;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(SpringExtension.class)
public class AccountServiceTest {
    private static AccountRepository repository;
    private static AccountService service;
    private static List<AccountDto> testDtos;
    private static LoremIpsum loremIpsum;
    private static AccountDtoConverter converter;

    @BeforeAll
    public static void init() {
        Random random = new Random();

        repository = mock(AccountRepository.class);
        service = new AccountService(repository);
        converter = new AccountDtoConverter();
        testDtos = new ArrayList<>();
        loremIpsum = LoremIpsum.getInstance();

        int currentYear = Calendar.getInstance().get(Calendar.YEAR); //to calculate max generation
        testDtos = Stream.generate(() -> AccountDto.builder()
                .name(loremIpsum.getName())
                .email(loremIpsum.getEmail())
                .generation(random.nextInt(currentYear - 2016))
                .clazz(random.nextInt(4))
                .number(random.nextInt(20))
                .build()).limit(10).collect(Collectors.toList());
    }

    @Test
    public void testAdd() {
        //Setting test environment
        int idxOfAddDto = new Random().nextInt(10);
        service.add(testDtos.get(idxOfAddDto));

        //Check AccountService delegated the addAccount logic to AccountRepository
        verify(repository, times(1)).save(converter.convertDtoToEntity(testDtos.get(idxOfAddDto)));

        //Logging test
        log.info("AccountServiceTest - testAdd");
        log.info("added user : " + testDtos.get(idxOfAddDto));
    }

    @Test
    public void testAddFailure() {
        //Setting test environment
        int idxOfAddDto = new Random().nextInt(10);
        service.add(testDtos.get(idxOfAddDto));
        when(repository.exists(any())).thenReturn(true);

        //Check AccountService delegated the addAccount logic to AccountRepository
        verify(repository, times(1)).save(converter.convertDtoToEntity(testDtos.get(idxOfAddDto)));

        //Logging test
        log.info("AccountServiceTest - testAddFailure");
        log.info("added user : " + testDtos.get(idxOfAddDto));
    }

    @Test
    public void testGet() {
        //Setting test environment
        int idxOfGetDto = new Random().nextInt(10);
        Account getEntity = converter.convertDtoToEntity(testDtos.get(idxOfGetDto));
        String email = getEntity.getEmail();
        when(repository.getById(anyString())).thenReturn(getEntity);

        //Check return of get
        assertEquals(service.get(email), converter.convertEntityToDto(getEntity));
        //Check AccountService delegated the getAccount logic to AccountRepository
        verify(repository).getById(email);

        //Logging test
        log.info("AccountServiceTest - testGet");
        log.info("getEntity : " + getEntity);
    }

    @Test
    public void testCount() {
        //Logging test (in code)
        log.info("AccountServiceTest - testCount");
        //Setting test environment
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        String name = "오병진";
        int generation = 4;
        int grade = 2;
        int clazz = 1;

        int returnValue;

        //Check count by name
        returnValue = new Random().nextInt(100);
        when(repository.countByName(name)).thenReturn(returnValue);
        int nameCount = service.count(AccountCountStrategy.NAME, name);

        assertEquals(nameCount, returnValue);
        verify(repository, atLeastOnce()).countByName(name);

        log.info("test countByName");
        log.info("expected return Value : " + returnValue);
        log.info("real return Value : " + nameCount);
        //Check count by generation
        returnValue = new Random().nextInt(100);
        when(repository.countByGeneration(generation)).thenReturn(returnValue);
        int genCount = service.count(AccountCountStrategy.GENERATION, generation);

        assertEquals(genCount, returnValue);
        verify(repository, atLeastOnce()).countByGeneration(generation);

        log.info("test countByGeneration");
        log.info("expected return Value : " + returnValue);
        log.info("real return Value : " + genCount);
        //Check count by grade
        returnValue = new Random().nextInt(100);
        when(repository.countByGeneration(currentYear - 2015 - grade)).thenReturn(returnValue);
        int gradeCount = service.count(AccountCountStrategy.GRADE, grade);

        assertEquals(gradeCount, returnValue);
        verify(repository, atLeastOnce()).countByGeneration(currentYear - 2015 - grade);

        log.info("test countByGrade");
        log.info("expected return Value : " + returnValue);
        log.info("real return Value : " + gradeCount);
        //Check count by clazz
        returnValue = new Random().nextInt(100);
        when(repository.countByClazz(clazz)).thenReturn(returnValue);
        int clazzCount = service.count(AccountCountStrategy.CLAZZ, clazz);

        assertEquals(clazzCount, returnValue);
        verify(repository, atLeastOnce()).countByClazz(clazz);

        log.info("test countByClazz");
        log.info("expected return Value : " + returnValue);
        log.info("real return Value : " + clazzCount);
    }

    @Test
    public void testCountFailure() {
        Exception e = assertThrows(UnknownStrategyException.class, () -> service.count(null, 1));
        Exception e2 = assertThrows(WrongConditionTypeException.class, () -> service.count(AccountCountStrategy.GRADE, "condition must be integer"));

        //Logging test
        log.info("AccoutnServiceTest - testCountFailure");
        log.info("exception : " + e.getClass().getSimpleName());
        log.info("message : " + e.getMessage());
        log.info("exception : " + e2.getClass().getSimpleName());
        log.info("message : " + e2.getMessage());
    }

    @Test
    public void testUpdateStudentId() {
        //Setting test environment
        int studentId = 2218;
        int idxOfGetDto = new Random().nextInt(10);
        Account testDto = converter.convertDtoToEntity(testDtos.get(idxOfGetDto));
        when(repository.getById(testDto.getEmail())).thenReturn(testDto);

        //Predict the results to create a comparison group
        //---->translateStudentIdToAccountDto 의 로직을 수동으로 구현한다
        Calendar cal = Calendar.getInstance();
        int currentYear = cal.get(Calendar.YEAR);
        int grade = studentId / 1000;
        int gen = currentYear - 2015 - grade;
        int clazz = (studentId % 1000) / 100;
        int number = studentId % 100;
        //수동구현한 학번 예상값을 통하여 반환 예상값을 구한다
        AccountDto expectedResultDto = AccountDto.builder()
                .name(testDto.getName()).email(testDto.getEmail())
                .generation(gen).clazz(clazz).number(number)
                .build();
        String email = testDto.getEmail();

        //Check method is run correctly
        service.updateStudentId(email, studentId);
        verify(repository).save(converter.convertDtoToEntity(expectedResultDto));

        log.info("AccountServiceTest - testUpdateStudentId");
        log.info("insert value : " + testDto);
        log.info("expected & real result : " + expectedResultDto);
    }
}

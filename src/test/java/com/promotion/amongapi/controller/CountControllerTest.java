package com.promotion.amongapi.controller;

import com.promotion.amongapi.logic.AccountCountStrategy;
import com.promotion.amongapi.service.AccountService;
import com.thedeanda.lorem.LoremIpsum;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Calendar;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(SpringExtension.class)
public class CountControllerTest {
    private static CountController countController;
    private static AccountService service;

    private static LoremIpsum lorem;
    private static Random random;

    @BeforeAll
    public static void init() { //init test objects
        service = mock(AccountService.class);
        countController = new CountController(service);

        lorem = LoremIpsum.getInstance();
        random = new Random();
    }

    @Test
    public void countName() {
        //Prepare test environment
        //---->Set metadata to testing
        String name = lorem.getName();
        int randomReturn = new Random().nextInt();
        when(service.count(AccountCountStrategy.NAME, name)).thenReturn(randomReturn);

        //---->Get count
        int count = countController.countName(name);

        //Logging test
        log.info("CountControllerTest - countName");
        log.info("random return : " + count);
        log.info("count : " + count);

        //Check count
        assertEquals(count, randomReturn);
    }

    @Test
    public void countGeneration() {
        //Prepare test environment
        //---->Set metadata to testing
        int maxGeneration = Calendar.getInstance().get(Calendar.YEAR) - 2016;
        int generation = new Random().nextInt(maxGeneration) + 1;
        int randomReturn = new Random().nextInt();
        when(service.count(AccountCountStrategy.GENERATION, generation)).thenReturn(randomReturn);

        //---->Get count
        int count = countController.countGeneration(generation);

        //Logging test
        log.info("CountControllerTest - countGeneration");
        log.info("random return : " + count);
        log.info("count : " + count);

        //Check count
        assertEquals(count, randomReturn);
    }

    @Test
    public void countGrade() {
        //Prepare test environment
        //---->Set metadata to testing
        int grade = new Random().nextInt(3) + 1;
        int randomReturn = new Random().nextInt();
        when(service.count(AccountCountStrategy.GRADE, grade)).thenReturn(randomReturn);

        //---->Get count
        int count = countController.countGrade(grade);

        //Logging test
        log.info("CountControllerTest - countGrade");
        log.info("random return : " + count);
        log.info("count : " + count);

        //Check count
        assertEquals(count, randomReturn);
    }

    @Test
    public void countClazz() {
        //Prepare test environment
        //---->Set metadata to testing
        int clazz = new Random().nextInt(4) + 1;
        int randomReturn = new Random().nextInt();
        when(service.count(AccountCountStrategy.CLAZZ, clazz)).thenReturn(randomReturn);

        //---->Get count
        int count = countController.countClazz(clazz);

        //Logging test
        log.info("CountControllerTest - countClazz");
        log.info("random return : " + count);
        log.info("count : " + count);

        //Check count
        assertEquals(count, randomReturn);
    }
}

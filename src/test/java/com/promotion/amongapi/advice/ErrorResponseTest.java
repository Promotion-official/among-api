package com.promotion.amongapi.advice;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class ErrorResponseTest {
    @Test
    public void testConstructor() {
        //Prepare test environment
        ErrorStatus[] allStatus = ErrorStatus.values();
        int randomStatusIdx = new Random().nextInt(allStatus.length);

        ErrorStatus randomStatus = allStatus[randomStatusIdx];

        ErrorResopnse responseByCode = new ErrorResopnse("", randomStatus.getCode());
        ErrorResopnse responseByStatus = new ErrorResopnse("", randomStatus);

        //Logging test
        log.info("ErrorResponseTest - testConstructor");
        log.info("responseByCode : " + responseByCode);
        log.info("responseByStatus : " + responseByStatus);

        //Check error responses
        assertEquals(responseByCode, responseByStatus);
    }
}

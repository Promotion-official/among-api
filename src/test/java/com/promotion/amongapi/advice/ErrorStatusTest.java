package com.promotion.amongapi.advice;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class ErrorStatusTest {
    @Test
    public void testOf() {
        //Prepare test environment
        //---->Set metadata to testing
        ErrorStatus[] allStatuses = ErrorStatus.values();
        int numOfStatus = allStatuses.length;
        int randomStatusIdx = new Random().nextInt(numOfStatus);

        //---->Set statuses
        ErrorStatus randomStatus = allStatuses[randomStatusIdx];
        ErrorStatus ofStatus = ErrorStatus.of(randomStatus.getCode());

        //Logging test
        log.info("ErrorStatusTest - testOf");
        log.info("expected result : " + randomStatus);
        log.info("of status : " + ofStatus);

        //Check statuses
        assertEquals(ofStatus, randomStatus);
    }
}

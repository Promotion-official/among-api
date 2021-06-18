package com.promotion.amongapi.advice.handler;

import com.promotion.amongapi.advice.ErrorResopnse;
import com.promotion.amongapi.advice.ErrorStatus;
import com.thedeanda.lorem.LoremIpsum;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class EntityNotFoundHandlerTest {
    private static EntityNotFountHandler handler;
    private static LoremIpsum lorem;

    @BeforeAll
    public static void init() {
        handler = new EntityNotFountHandler();
        lorem = LoremIpsum.getInstance();
    }

    @Test
    public void testHandleException() {
        String message = lorem.getTitle(3);

        EntityNotFoundException exception = new EntityNotFoundException(message);
        ResponseEntity<ErrorResopnse> expectedResponse =
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResopnse(message, ErrorStatus.ENTITY_NOT_FOUND));
        ResponseEntity<ErrorResopnse> response = handler.handleException(exception);

        assertEquals(response, expectedResponse);

        log.info("response : " + response);
        log.info("expected response : " + expectedResponse);
    }
}

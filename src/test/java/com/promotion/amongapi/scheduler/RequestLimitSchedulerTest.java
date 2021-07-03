package com.promotion.amongapi.scheduler;

import com.promotion.amongapi.service.AuthorizeKeyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class RequestLimitSchedulerTest {
    AuthorizeKeyService service;
    RequestLimitScheduler scheduler;

    @BeforeEach
    public void init() {
        service = mock(AuthorizeKeyService.class);
        scheduler = new RequestLimitScheduler(service);
    }

    @Test
    public void testClear() {
        scheduler.clear();
        verify(service).clear();
    }
}
package com.promotion.amongapi.interceptor;

import com.promotion.amongapi.service.AuthorizeKeyService;
import com.thedeanda.lorem.LoremIpsum;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
public class AuthorizeKeyInterceptorTest {
    AuthorizeKeyService service;
    AuthorizeKeyInterceptor interceptor;

    @BeforeEach
    public void init() {
        service = mock(AuthorizeKeyService.class);
        interceptor = new AuthorizeKeyInterceptor(service);
    }

    @Test
    public void testPreHandle() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Object handler = mock(Object.class);

        String authorizeKey = LoremIpsum.getInstance().getWords(1);

        when(request.getParameter("authorize_key")).thenReturn(authorizeKey);

        assertTrue(interceptor.preHandle(request, response, handler));
        verify(service).count(authorizeKey);
    }

    @Test
    public void testPreHandleFailure() throws IOException {
        //Logging test
        log.info("AuthorizeKeyInterceptorTest - testPreHandleFailure");

        //Prepare test environment - create mock objects
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Object handler = mock(Object.class);
        PrintWriter writer = mock(PrintWriter.class);
        //set exception reason
        String exceptionReason = LoremIpsum.getInstance().getWords(10);

        //Prepare test environment - set mock condition
        when(request.getParameter("authorize_key")).thenReturn("authorize_key");
        when(response.getWriter()).thenReturn(writer);

        doThrow(new EntityNotFoundException(exceptionReason)).when(service).count(anyString());

        //Check preHandle method when authorize key doesn't exceeded
        boolean result = interceptor.preHandle(request, response, handler);
        assertFalse(result);
        verify(response).setStatus(HttpStatus.BAD_REQUEST.value());
        verify(writer).write(exceptionReason);

        //Logging test
        log.info("result : " + result);
    }
}

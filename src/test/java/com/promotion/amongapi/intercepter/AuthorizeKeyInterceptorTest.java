package com.promotion.amongapi.intercepter;

import com.promotion.amongapi.service.AuthorizeKeyService;
import com.thedeanda.lorem.LoremIpsum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class AuthorizeKeyInterceptorTest {
    AuthorizeKeyService service;
    AuthorizeKeyInterceptor interceptor;

    @BeforeEach
    public void init() {
        service = mock(AuthorizeKeyService.class);
        interceptor = new AuthorizeKeyInterceptor(service);
    }

    @Test
    public void testPreHandle() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Object handler = mock(Object.class);

        String authorizeKey = LoremIpsum.getInstance().getWords(1);

        when(request.getParameter("authorize_key")).thenReturn(authorizeKey);

        assertTrue(interceptor.preHandle(request, response, handler));
        verify(service).count(authorizeKey);
    }
}

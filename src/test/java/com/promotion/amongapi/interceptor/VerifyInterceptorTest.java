package com.promotion.amongapi.interceptor;

import com.promotion.amongapi.advice.exception.PermissionDeniedPageAccessException;
import com.promotion.amongapi.annotation.Verify;
import com.promotion.amongapi.domain.Permission;
import com.promotion.amongapi.domain.dto.AuthorizeKeyDto;
import com.promotion.amongapi.service.AuthorizeKeyService;
import com.thedeanda.lorem.LoremIpsum;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Log4j2
public class VerifyInterceptorTest {
    private AuthorizeKeyService service;
    private VerifyInterceptor interceptor;
    private HandlerMethod handler;
    private Verify verify;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private AuthorizeKeyDto key;

    @BeforeEach
    public void init() {
        service = mock(AuthorizeKeyService.class);
        interceptor = new VerifyInterceptor(service);
        handler = mock(HandlerMethod.class);
        verify = mock(Verify.class);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
    }

    @BeforeEach
    public void setEnvironment() {
        String authorizeKey = LoremIpsum.getInstance().getWords(1);

        when(verify.perm()).thenReturn(new Permission[]{Permission.ADMINISTRATOR});

        key = mock(AuthorizeKeyDto.class);
        when(service.getKey(authorizeKey)).thenReturn(key);

        Class clazz = Verify.class;
        when(handler.getMethodAnnotation(Verify.class)).thenReturn(verify);
        when(handler.getBeanType()).thenReturn(clazz);
        when(handler.getBeanType().getAnnotation(Verify.class)).thenReturn(null);

        when(request.getParameter("authorize_key")).thenReturn(authorizeKey);
    }

    @Test
    public void testPreHandle() {
        //Logging test start
        log.info("VerifyInterceptorTest - testPreHandle");

        //Setting test environment
        when(key.getPerm()).thenReturn(Permission.ADMINISTRATOR);

        //check return
        assertTrue(interceptor.preHandle(request, response, handler));
    }

    @Test
    public void testPreHandleFailure() {
        //Logging test start
        log.info("VerifyInterceptorTest - testPreHandleFailure");

        //Setting test environment
        when(key.getPerm()).thenReturn(Permission.OPERATOR);

        //check is failure
        assertThrows(PermissionDeniedPageAccessException.class, () -> interceptor.preHandle(request, response, handler));
    }
}

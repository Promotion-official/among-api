package com.promotion.amongapi.interceptor;

import com.promotion.amongapi.advice.exception.AuthorizeKeyNotFoundException;
import com.promotion.amongapi.service.AuthorizeKeyService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class AuthorizeKeyInterceptor implements HandlerInterceptor {
    private final AuthorizeKeyService service;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String paramKey = request.getParameter("authorize_key");
        try {
            service.count(paramKey);
        } catch (EntityNotFoundException e) {
            AuthorizeKeyNotFoundException exception = new AuthorizeKeyNotFoundException(paramKey);
            exception.initCause(e);
            throw exception;
        }
        return true;
    }
}

package com.promotion.amongapi.intercepter;

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
        } catch (EntityNotFoundException | InvalidDataAccessApiUsageException e) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.getWriter().write(e.getMessage());
            return false;
        }
        return true;
    }
}

package com.promotion.amongapi.intercepter;

import com.promotion.amongapi.advice.ErrorResopnse;
import com.promotion.amongapi.advice.ErrorStatus;
import com.promotion.amongapi.service.AuthorizeKeyService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@AllArgsConstructor
public class AuthorizeKeyInterceptor implements HandlerInterceptor {
    private final AuthorizeKeyService service;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String paramKey = request.getParameter("authorize_key");
        try {
            service.count(paramKey);
        } catch (EntityNotFoundException e) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.getWriter().write(e.getMessage());
            return false;
        }
        /*
        TODO Jeeinho
         만약 paramkey 가 parameter에 없을경우
         InvalidDataAccessApiUsageException 발생, advice 에서 처리바람
         2021-06-29 8:26 PM KST
        */
        return true;
    }
}

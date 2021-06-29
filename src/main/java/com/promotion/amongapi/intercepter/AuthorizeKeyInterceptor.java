package com.promotion.amongapi.intercepter;

import com.promotion.amongapi.service.AuthorizeKeyService;
import lombok.AllArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@AllArgsConstructor
public class AuthorizeKeyInterceptor implements HandlerInterceptor {
    private final AuthorizeKeyService service;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String paramKey = request.getParameter("authorize_key");
        service.count(paramKey);
        /*
        TODO Jeeinho
         만약 paramkey 가 parameter에 없을경우
         InvalidDataAccessApiUsageException 발생, advice 에서 처리바람
         2021-06-29 8:26 PM KST
        */
        return true;
    }
}

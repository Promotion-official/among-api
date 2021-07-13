package com.promotion.amongapi.interceptor;

import com.promotion.amongapi.advice.exception.PermissionDeniedPageAccessException;
import com.promotion.amongapi.annotation.Verify;
import com.promotion.amongapi.domain.Permission;
import com.promotion.amongapi.service.AuthorizeKeyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.util.Arrays;

@RequiredArgsConstructor
@Slf4j
public class VerifyInterceptor implements HandlerInterceptor {
    private final AuthorizeKeyService service;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws PermissionDeniedPageAccessException {
        HandlerMethod method = (HandlerMethod) handler;
        String authorizeKey = request.getParameter("authorize_key");
        log.info("authorize key : " + authorizeKey);

        boolean hasAnnotation = checkAnnotation(method, Verify.class);
        if(hasAnnotation) {
            Verify verify = getAnnotation(method, Verify.class); //Annotation 불러오기

            Permission[] usablePermissions = verify.perm(); //사용권한 확인
            Permission requestPermission = service.getKey(authorizeKey).getPerm(); //요청권한 확인
            log.info("usable permission: " + Arrays.toString(usablePermissions));
            log.info("request permission: " + requestPermission);

            for (Permission usablePermission : usablePermissions) //요청권한과 사용권한 대조를 위한 loop
                if (usablePermission.equals(requestPermission)) //요청권한이 있을경우
                    return true;
            throw new PermissionDeniedPageAccessException(requestPermission, usablePermissions);
        } else return true;
    }

    //handling 되는 메서드에 해당 형의 annotation 이 있는지를 검사한다.
    private boolean checkAnnotation(HandlerMethod handler, Class<? extends Annotation> clazz) {
        return handler.getMethodAnnotation(clazz) != null || handler.getBeanType().getAnnotation(clazz) != null;
    }

    //handling 되는 메서도에 붙어있는 해당 형의 annotation 을 반환한다 (없을시 null 반환)
    private <T extends Annotation> T getAnnotation(HandlerMethod handler, Class<T> clazz) {
        T result = handler.getMethodAnnotation(clazz);
        if (result != null) return result;
        else return handler.getBeanType().getAnnotation(clazz);
    }
}
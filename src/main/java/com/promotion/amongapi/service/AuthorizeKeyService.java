package com.promotion.amongapi.service;

import com.promotion.amongapi.advice.exception.PermissionNotMatchException;
import com.promotion.amongapi.advice.exception.RequestLimitExceededException;
import com.promotion.amongapi.domain.Permission;
import com.promotion.amongapi.domain.converter.AuthorizeKeyConverter;
import com.promotion.amongapi.domain.dto.AuthorizeKeyDto;
import com.promotion.amongapi.domain.entity.AuthorizeKey;
import com.promotion.amongapi.repository.AuthorizeKeyRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@RequiredArgsConstructor
@Service
public class AuthorizeKeyService {
    private final AuthorizeKeyRepository repository;
    private final AuthorizeKeyConverter converter;
    private static final Map<String, Integer> requestStatus = new HashMap<>();

    @Transactional(readOnly = true)
    public void count(String authorizeKey) {
        //Get Datas
        //---->Get authorize key
        AuthorizeKey entity = repository.getById(authorizeKey);
        AuthorizeKeyDto dto = converter.convertEntityToDto(entity);
        //---->Get request limit
        RequestLimitPermission rlp = RequestLimitPermission.of(dto.getPerm());
        int requestLimit = rlp.getMaxRequestLimit();
        //---->Get request count
        Integer requestCountReference = requestStatus.get(authorizeKey);
        int requestCount = requestCountReference == null ? 0 : requestCountReference; //optional 보다 삼항연산자 표현식이 더 짧다

        //Count request
        if(requestLimit <= requestCount) throw new RequestLimitExceededException(requestLimit, requestCount);
        else requestCount++;

        //Save count
        requestStatus.put(authorizeKey, requestCount);
    }

    public void clear() {
        requestStatus.clear();
    }

    @ToString
    @RequiredArgsConstructor
    protected enum RequestLimitPermission {
        DEVELOPER(Permission.DEVELOPER, 10),
        PRODUCT(Permission.PRODUCT, 20),
        OPERATOR(Permission.OPERATOR, 100),
        ADMINISTRATOR(Permission.ADMINISTRATOR, 100000);

        @Getter
        private final Permission perm;
        @Getter
        private final int maxRequestLimit;

        public static RequestLimitPermission of(Permission perm) {
            AtomicReference<RequestLimitPermission> resultReference = new AtomicReference<>();
            Arrays.stream(values()).forEach(requestLimitPermission -> {
                if(requestLimitPermission.getPerm().equals(perm)) {
                    resultReference.set(requestLimitPermission);
                }
            });

            RequestLimitPermission result = resultReference.get();
            if(result == null) throw new PermissionNotMatchException();
            return result;
        }
    }
}

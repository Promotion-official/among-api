package com.promotion.amongapi.service;

import com.promotion.amongapi.advice.exception.RequestLimitExceededException;
import com.promotion.amongapi.domain.Permission;
import com.promotion.amongapi.domain.converter.AuthorizeKeyConverter;
import com.promotion.amongapi.domain.entity.AuthorizeKey;
import com.promotion.amongapi.repository.AuthorizeKeyRepository;
import com.thedeanda.lorem.LoremIpsum;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Slf4j
public class AuthorizeKeyServiceTest {
    private AuthorizeKeyRepository repository;
    private AuthorizeKeyService service;

    @BeforeEach
    public void init() {
        repository = mock(AuthorizeKeyRepository.class);
        service = new AuthorizeKeyService(repository, new AuthorizeKeyConverter());
    }

    @Test
    public void testCount() {
        //Prepare test environment
        String key = "among_key" + LoremIpsum.getInstance().getWords(3);
        Permission perm = Permission.DEVELOPER;
        AuthorizeKeyService.RequestLimitPermission rlp = AuthorizeKeyService.RequestLimitPermission.of(perm);
        AuthorizeKey authorizeKey = new AuthorizeKey(key, perm.ordinal());

        int methodLoopTimes = rlp.getMaxRequestLimit() + 1;
        AtomicLong timeOfThrowExceptionWhenLoop = new AtomicLong();

        when(repository.getById(anyString())).thenReturn(authorizeKey);

        //Logging test
        log.info("authorize key : " + authorizeKey);
        log.info("request limit permission : " + rlp);
        log.info("method loop : " + methodLoopTimes);

        //Check throw exception (RequestLimitExceededException)
        assertThrows(RequestLimitExceededException.class, () -> {
            for(long i = 0; i < methodLoopTimes; i++) {
                try {
                    service.count(key);
                } catch (Exception e) {
                    timeOfThrowExceptionWhenLoop.set(i);
                    //Logging test
                    log.info("method throw exception - " + e.getClass().getSimpleName());
                    throw e;
                }
            }
        });

        //Logging test
        log.info("request times on loop : " + timeOfThrowExceptionWhenLoop.get());

        //Check request limit
        assertEquals(timeOfThrowExceptionWhenLoop.get(), rlp.getMaxRequestLimit());
    }

    @Test
    public void testRequestLimitPermission() {
        //Prepare test environment
        Permission[] allPerms = Permission.values();
        Permission randomPerm = allPerms[new Random().nextInt(allPerms.length)];
        AuthorizeKeyService.RequestLimitPermission requestLimitPermission = AuthorizeKeyService.RequestLimitPermission.of(randomPerm);

        //Logging test
        log.info("AuthorizeKeyCounterTest - testRequestLimitPermission");
        log.info("permission : " + randomPerm);
        log.info("request limit permission : " + requestLimitPermission);

        //Check of method
        assertEquals(randomPerm, requestLimitPermission.getPerm());
    }
}

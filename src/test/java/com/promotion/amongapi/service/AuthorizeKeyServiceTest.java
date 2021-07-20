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

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.*;
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
    public void testGetKey() {
        //Logging test start
        log.info("AuthorizeKeyServiceTest - testGetKey");

        //Prepare test environment
        String authorizeKey = LoremIpsum.getInstance().getEmail();
        int permissionId = new Random().nextInt(Permission.values().length);
        Permission perm = Permission.values()[permissionId];

        when(repository.getById(authorizeKey)).thenReturn(new AuthorizeKey(authorizeKey, perm.name()));

        assertEquals(service.getKey(authorizeKey).getPerm(), Permission.of(perm.name()));
    }

    @Test
    public void testCount() {
        //Logging test start
        log.info("AuthorizeKeyServiceTest - testCount");

        //Prepare test environment
        String key = "among_key" + LoremIpsum.getInstance().getWords(3);
        Permission randomPerm = getRandomPermission();
        AuthorizeKeyService.RequestLimitPermission rlp = AuthorizeKeyService.RequestLimitPermission.of(randomPerm);
        AuthorizeKey authorizeKey = new AuthorizeKey(key, randomPerm.name());

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
    public void testClear() throws NoSuchFieldException, IllegalAccessException {
        //Logging test start
        log.info("AuthorizeKeyServiceTest - testClear");

        //Prepare test environment
        int maxMethodLoop = 10;
        int methodLoop = new Random().nextInt(maxMethodLoop) + 1;

        //Logging test
        log.info("method loop : " + methodLoop + "(<=10)");

        //Get requestStatus to reflection
        Field field = service.getClass().getDeclaredField("requestStatus");
        field.setAccessible(true);
        Map<String, Integer> requestStatus = (Map<String, Integer>) field.get(service);

        //Logging test
        log.info("requestStatus (before add el) : " + requestStatus);

        //Add element in requestStatus
        for(int i = 0; i < methodLoop; i++)
            requestStatus.put(LoremIpsum.getInstance().getWords(1), new Random().nextInt());

        //Logging test
        log.info("requestStatus (after add el) : " + requestStatus);

        //Clear element to AuthorizeKeyService's method
        service.clear();
        boolean isEmpty = requestStatus.isEmpty();

        //LoggingTest
        log.info("map is empty : " + isEmpty);

        //Check requestStatus is Empty
        assertTrue(isEmpty);
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

    private Permission getRandomPermission() {
        Permission[] allPerms = Permission.values();
        return allPerms[new Random().nextInt(allPerms.length)];
    }
}

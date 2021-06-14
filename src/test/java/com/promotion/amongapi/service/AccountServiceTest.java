package com.promotion.amongapi.service;

import com.promotion.amongapi.dto.AccountDto;
import com.promotion.amongapi.repository.AccountRepository;
import com.thedeanda.lorem.LoremIpsum;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(SpringExtension.class)
public class AccountServiceTest {
    private static AccountRepository repository;
    private static AccountService service;
    private static List<AccountDto> testDtos;
    private static LoremIpsum loremIpsum;

    @BeforeAll
    public static void init() {
        Random random = new Random();

        repository = mock(AccountRepository.class);

        service = new AccountService(repository);
        loremIpsum = LoremIpsum.getInstance();
        testDtos = new ArrayList<>();

        testDtos = Stream.generate(() -> AccountDto.builder()
                .name(loremIpsum.getName())
                .email(loremIpsum.getEmail())
                .generation(random.nextInt(100))
                .clazz(random.nextInt(4))
                .number(random.nextInt(20))
                .build()).limit(10).collect(Collectors.toList());
    }

    @Test
    public void testAdd() {
        //Setting test environment
        int idxOfAddDto = new Random().nextInt(10);
        service.add(testDtos.get(idxOfAddDto));

        //Check AccountService delegated the addAccount logic to AccountRepository
        verify(repository, times(1)).save(testDtos.get(idxOfAddDto));

        //Logging test
        log.info("AccountServiceTest - testAdd");
        log.info("added user : " + testDtos.get(idxOfAddDto));
    }

    @Test
    public void testGet() {
        //Setting test environment
        int idxOfGetDto = new Random().nextInt(10);
        AccountDto getDto = testDtos.get(idxOfGetDto);
        String email = getDto.getEmail();
        when(repository.getAccountDtoByEmail(anyString())).thenReturn(getDto);

        //Check return of get
        assertEquals(service.get(email), getDto);
        //Check AccountService delegated the getAccount logic to AccountRepository
        verify(repository).getAccountDtoByEmail(email);
    }
}

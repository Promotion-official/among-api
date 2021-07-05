package com.promotion.amongapi.scheduler;

import com.promotion.amongapi.service.AuthorizeKeyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RequestLimitScheduler {
    private final AuthorizeKeyService service;

    @Scheduled(fixedDelay = 1000 * 60) //one minute
    public void clear() {
        service.clear();
        log.info("인증키의 분당요청횟수 초기화 완료!"); //TODO JeeInho | 추후 Logger 를 통해서 Logging 예정
    }
}

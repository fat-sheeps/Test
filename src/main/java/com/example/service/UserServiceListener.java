package com.example.service;

import com.example.domain.UserEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserServiceListener {
    @Async
    @EventListener
    public void onApplicationEvent(UserEvent userEvent) throws InterruptedException {
        Thread.sleep(1000);
        String userId;
        log.info("UserServiceListener userId:{}", userEvent.getUserId());
    }
}

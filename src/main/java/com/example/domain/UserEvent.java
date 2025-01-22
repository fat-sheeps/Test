package com.example.domain;

import org.springframework.context.ApplicationEvent;

import java.time.Clock;

public class UserEvent extends ApplicationEvent {

    private String userId;

    public UserEvent(Object source) {
        super(source);
    }
    public UserEvent(Object source, String userId) {
        super(source);
        this.userId = userId;
    }

    public UserEvent(Object source, Clock clock) {
        super(source, clock);
    }

    public String getUserId() {
        return userId;
    }
}

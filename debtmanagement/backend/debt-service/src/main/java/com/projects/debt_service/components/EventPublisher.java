package com.projects.debt_service.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.projects.debt_service.events.DebtDefaultedEvent;

@Component
public class EventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public EventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publish(DebtDefaultedEvent event) {
        applicationEventPublisher.publishEvent(event);
    }
}
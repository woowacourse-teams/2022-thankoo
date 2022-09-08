package com.woowacourse.thankoo.common.event;

import org.springframework.context.ApplicationEventPublisher;

public class Events {

    private static ApplicationEventPublisher applicationEventPublisher;

    static void setApplicationEventPublisher(final ApplicationEventPublisher applicationEventPublisher) {
        Events.applicationEventPublisher = applicationEventPublisher;
    }

    public static void publish(final Object event) {
        if (applicationEventPublisher != null) {
            applicationEventPublisher.publishEvent(event);
        }
    }
}

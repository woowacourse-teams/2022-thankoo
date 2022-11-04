package com.woowacourse.thankoo.alarm.application;

import com.woowacourse.thankoo.common.dto.AlarmEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AlarmEventListener {

    private final AlarmService alarmService;

    @Async(value = "asyncTaskExecutor")
    @EventListener
    public void handle(final AlarmEvent alarmEvent) {
        log.debug("alarm event = {}", alarmEvent);
        alarmService.send(alarmEvent.toAlarmSpecification());
    }
}

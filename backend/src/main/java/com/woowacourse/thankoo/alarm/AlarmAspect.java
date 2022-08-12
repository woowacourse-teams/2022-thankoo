package com.woowacourse.thankoo.alarm;

import com.woowacourse.thankoo.alarm.dto.AlarmMessageRequest;
import com.woowacourse.thankoo.alarm.support.AlarmManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("prod")
@Aspect
@Component
@RequiredArgsConstructor
public class AlarmAspect {

    private final AlarmSender alarmSender;

    @After("@annotation(com.woowacourse.thankoo.alarm.support.Alarm)")
    public void sendMessage() {
        AlarmMessageRequest messageRequest = AlarmManager.getResources();
        List<String> emails = messageRequest.getEmails();
        for (String email : emails) {
            alarmSender.send(email, messageRequest.getAlarmMessage());
        }
        AlarmManager.clear();
    }
}

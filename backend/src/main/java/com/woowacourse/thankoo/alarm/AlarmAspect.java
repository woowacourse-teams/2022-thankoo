package com.woowacourse.thankoo.alarm;

import com.woowacourse.thankoo.alarm.infrastructure.dto.AttachmentsRequest;
import com.woowacourse.thankoo.alarm.support.AlarmManager;
import com.woowacourse.thankoo.alarm.support.Message;
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
        Message message = AlarmManager.getResources();
        for (String email : message.getEmails()) {
            alarmSender.send(email, AttachmentsRequest.from(message.getTitle(), message.getContents()));
        }
        AlarmManager.clear();
    }
}

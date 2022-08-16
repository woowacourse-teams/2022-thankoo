package com.woowacourse.thankoo.alarm;

import com.woowacourse.thankoo.alarm.infrastructure.dto.AttachmentsRequest;
import com.woowacourse.thankoo.alarm.support.AlarmManager;
import com.woowacourse.thankoo.alarm.support.AlarmMessageRequest;
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
        List<AlarmMessageRequest> requests = AlarmManager.getResources();
        for (AlarmMessageRequest request : requests) {
            List<String> detailMessages = request.getDetailMessages();
            alarmSender.send(request.getEmail(), AttachmentsRequest.from(request.getTitle(), detailMessages));
        }
        AlarmManager.clear();
    }
}

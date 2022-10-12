package com.woowacourse.thankoo.alarm.application.strategy;

import com.woowacourse.thankoo.alarm.application.MessageFormStrategy;
import com.woowacourse.thankoo.alarm.application.dto.Message;
import com.woowacourse.thankoo.alarm.domain.Alarm;
import com.woowacourse.thankoo.alarm.domain.AlarmType;
import com.woowacourse.thankoo.alarm.exception.InvalidAlarmException;
import com.woowacourse.thankoo.alarm.infrastructure.AlarmLinkGenerator;
import com.woowacourse.thankoo.common.exception.ErrorType;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HeartMessageFormStrategy implements MessageFormStrategy {

    private static final int CONTENT_SIZE = 2;
    private static final int SENDER_ID_INDEX = 0;
    private static final int COUNT_INDEX = 1;

    private static final String TITLE = "{0}님이 당신에게 마음을 {1}번 보냈어요 ❤️";
    private static final String TITLE_LINK = "/organization/{0}/hearts";

    private final AlarmMemberProvider alarmMemberProvider;
    private final AlarmLinkGenerator alarmLinkGenerator;

    @Override
    public Message createFormat(final Alarm alarm) {
        validateContentSize(alarm);
        List<String> receiverEmails = alarmMemberProvider.getReceiverEmails(alarm.getTargetIds());
        String senderName = alarmMemberProvider.getSenderName(alarm.getContentAt(SENDER_ID_INDEX));

        return Message.builder()
                .email(receiverEmails)
                .title(MessageFormat.format(TITLE, senderName, String.valueOf(alarm.getContentAt(COUNT_INDEX))))
                .titleLink(alarmLinkGenerator.createUrl(MessageFormat.format(TITLE_LINK, alarm.getOrganizationId())))
                .contents(Collections.emptyList())
                .build();
    }

    private void validateContentSize(final Alarm alarm) {
        if (!alarm.hasContentsSize(CONTENT_SIZE)) {
            throw new InvalidAlarmException(ErrorType.INVALID_ALARM_FORMAT);
        }
    }

    @Override
    public AlarmType getAlarmType() {
        return AlarmType.HEART_SENT;
    }
}

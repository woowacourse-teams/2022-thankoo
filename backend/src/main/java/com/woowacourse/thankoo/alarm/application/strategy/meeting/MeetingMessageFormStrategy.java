package com.woowacourse.thankoo.alarm.application.strategy.meeting;

import com.woowacourse.thankoo.alarm.application.MessageFormStrategy;
import com.woowacourse.thankoo.alarm.application.dto.Message;
import com.woowacourse.thankoo.alarm.application.strategy.AlarmMemberProvider;
import com.woowacourse.thankoo.alarm.domain.Alarm;
import com.woowacourse.thankoo.alarm.domain.AlarmType;
import com.woowacourse.thankoo.alarm.domain.Emails;
import com.woowacourse.thankoo.alarm.infrastructure.AlarmLinkGenerator;
import java.text.MessageFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MeetingMessageFormStrategy implements MessageFormStrategy {

    private static final String PRETEXT = "\uD83E\uDD70 오늘은 미팅이 있는 날이에요!!";
    private static final String ACCEPT_TITLE_LINK = "/organizations/{0}";

    private final AlarmMemberProvider alarmMemberProvider;
    private final AlarmLinkGenerator alarmLinkGenerator;

    @Override
    public Message createFormat(final Alarm alarm) {
        Emails receiverEmails = alarmMemberProvider.getReceiverEmails(alarm.getTargetIds());

        return Message.builder()
                .email(receiverEmails.getEmails())
                .title(PRETEXT)
                .titleLink(createLink(alarm.getOrganizationId()))
                .build();
    }

    private String createLink(final Long organizationId) {
        return alarmLinkGenerator.createUrl(MessageFormat.format(ACCEPT_TITLE_LINK, organizationId));
    }

    @Override
    public AlarmType getAlarmType() {
        return AlarmType.MEETING_NOTICE;
    }
}

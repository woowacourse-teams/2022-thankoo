package com.woowacourse.thankoo.alarm.application.strategy.reservation;

import com.woowacourse.thankoo.alarm.application.dto.Message;
import com.woowacourse.thankoo.alarm.application.strategy.AlarmMemberProvider;
import com.woowacourse.thankoo.alarm.application.strategy.ReservationMessageFormStrategy;
import com.woowacourse.thankoo.alarm.domain.Alarm;
import com.woowacourse.thankoo.alarm.domain.AlarmType;
import com.woowacourse.thankoo.alarm.domain.Emails;
import com.woowacourse.thankoo.alarm.infrastructure.AlarmLinkGenerator;
import java.text.MessageFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationSentMessageFormStrategy extends ReservationMessageFormStrategy {

    private static final int CONTENT_SIZE = 3;

    private static final String TITLE_LINK = "/organizations/{0}/reservations";
    private static final String PRETEXT = "\uD83D\uDC9D 예약 요청이 도착했어요.";
    private static final String DATE = "예약 요청일 : {0}";

    private static final int DATE_INDEX = 2;

    private final AlarmMemberProvider alarmMemberProvider;
    private final AlarmLinkGenerator alarmLinkGenerator;

    @Override
    public Message createFormat(final Alarm alarm) {
        validateContentSize(alarm, CONTENT_SIZE);
        Emails receiverEmails = alarmMemberProvider.getReceiverEmails(alarm.getTargetIds());
        String senderName = alarmMemberProvider.getSenderName(alarm.getContentAt(SENDER_ID_INDEX));

        return Message.builder()
                .title(PRETEXT)
                .titleLink(createLink(alarm.getOrganizationId()))
                .email(receiverEmails.getEmails())
                .content(MessageFormat.format(SENDER, senderName))
                .content(MessageFormat.format(COUPON, alarm.getContentAt(COUPON_INDEX)))
                .content(MessageFormat.format(DATE, alarm.getContentAt(DATE_INDEX)))
                .build();
    }

    private String createLink(final Long organizationId) {
        return alarmLinkGenerator.createUrl(MessageFormat.format(TITLE_LINK, organizationId));
    }

    @Override
    public AlarmType getAlarmType() {
        return AlarmType.RESERVATION_SENT;
    }
}

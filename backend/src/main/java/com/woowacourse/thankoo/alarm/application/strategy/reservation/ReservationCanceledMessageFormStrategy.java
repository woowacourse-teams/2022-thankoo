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
public class ReservationCanceledMessageFormStrategy extends ReservationMessageFormStrategy {

    private static final int CONTENT_SIZE = 2;
    private static final String ACCEPT_TITLE_LINK = "/organizations/{0}";
    private static final String PRETEXT_CANCEL = "\uD83D\uDE05 예약이 취소되었어요ㅜ";

    private final AlarmMemberProvider alarmMemberProvider;
    private final AlarmLinkGenerator alarmLinkGenerator;

    @Override
    public Message createFormat(final Alarm alarm) {
        validateContentSize(alarm, CONTENT_SIZE);
        Emails receiverEmails = alarmMemberProvider.getReceiverEmails(alarm.getTargetIds());
        String senderName = alarmMemberProvider.getSenderName(alarm.getContentAt(SENDER_ID_INDEX));

        return Message.builder()
                .email(receiverEmails.getEmails())
                .title(PRETEXT_CANCEL)
                .titleLink(createLink(alarm.getOrganizationId()))
                .content(MessageFormat.format(SENDER, senderName))
                .content(MessageFormat.format(COUPON, alarm.getContentAt(COUPON_INDEX)))
                .build();
    }

    private String createLink(final Long organizationId) {
        return alarmLinkGenerator.createUrl(MessageFormat.format(ACCEPT_TITLE_LINK, organizationId));
    }

    @Override
    public AlarmType getAlarmType() {
        return AlarmType.RESERVATION_CANCELED;
    }
}

package com.woowacourse.thankoo.alarm.application.strategy.reservation;

import com.woowacourse.thankoo.alarm.application.dto.Message;
import com.woowacourse.thankoo.alarm.application.strategy.AlarmMemberProvider;
import com.woowacourse.thankoo.alarm.application.strategy.ReservationMessageFormStrategy;
import com.woowacourse.thankoo.alarm.domain.Alarm;
import com.woowacourse.thankoo.alarm.domain.AlarmType;
import com.woowacourse.thankoo.alarm.infrastructure.AlarmLinkGenerator;
import java.text.MessageFormat;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationRepliedMessageFormStrategy extends ReservationMessageFormStrategy {

    private static final int CONTENT_SIZE = 3;

    private static final String PRETEXT_RESPONSE = "\uD83D\uDC7B {0}님이 예약 요청에 응답했어요.";
    private static final int STATUS_INDEX = 2;
    private static final String ACCEPT_SIGN = "ACCEPT";
    private static final String ACCEPT_TITLE_LINK = "/meetings";
    private static final String RESERVATION_STATUS = "예약 상태 : {0}";
    private static final String ACCEPT_MESSAGE = "승인\uD83E\uDD70";
    private static final String DECLINE_MESSAGE = "거절\uD83D\uDE05";

    private final AlarmMemberProvider alarmMemberProvider;
    private final AlarmLinkGenerator alarmLinkGenerator;

    @Override
    public Message createFormat(final Alarm alarm) {
        validateContentSize(alarm, CONTENT_SIZE);
        List<String> receiverEmails = alarmMemberProvider.getReceiverEmails(alarm.getTargetIds());
        String senderName = alarmMemberProvider.getSenderName(alarm.getContentAt(SENDER_ID_INDEX));
        String status = alarm.getContentAt(STATUS_INDEX);
        return Message.builder()
                .title(MessageFormat.format(PRETEXT_RESPONSE, senderName))
                .titleLink(getTitleLink(status))
                .email(receiverEmails)
                .content(MessageFormat.format(COUPON, alarm.getContentAt(COUPON_INDEX)))
                .content(MessageFormat.format(RESERVATION_STATUS,
                        getStatusMessage(status)))
                .build();
    }

    @Override
    public AlarmType getAlarmType() {
        return AlarmType.RESERVATION_REPLIED;
    }

    private String getTitleLink(final String status) {
        return status.equals(ACCEPT_SIGN) ?
                alarmLinkGenerator.createUrl(ACCEPT_TITLE_LINK) : alarmLinkGenerator.getRootUrl();
    }

    private String getStatusMessage(final String status) {
        return status.equals(ACCEPT_SIGN) ? ACCEPT_MESSAGE : DECLINE_MESSAGE;
    }
}

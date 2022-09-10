package com.woowacourse.thankoo.alarm.application.strategy.reservation;

import com.woowacourse.thankoo.alarm.application.dto.Message;
import com.woowacourse.thankoo.alarm.application.strategy.AlarmMemberProvider;
import com.woowacourse.thankoo.alarm.application.strategy.ReservationMessageFormStrategy;
import com.woowacourse.thankoo.alarm.domain.Alarm;
import com.woowacourse.thankoo.alarm.domain.AlarmType;
import java.text.MessageFormat;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationSentMessageFormStrategy extends ReservationMessageFormStrategy {

    private static final int CONTENT_SIZE = 3;

    private static final String PRETEXT = "\uD83D\uDC9D 예약 요청이 도착했어요.";
    private static final String DATE = "예약 요청일 : {0}";

    private static final int DATE_INDEX = 2;

    private final AlarmMemberProvider alarmMemberProvider;

    @Override
    public Message createFormat(final Alarm alarm) {
        validateContentSize(alarm, CONTENT_SIZE);
        List<String> receiverEmails = alarmMemberProvider.getReceiverEmails(alarm.getTargetIds());
        String senderName = alarmMemberProvider.getSenderName(alarm.getContents().get(SENDER_ID_INDEX));

        return Message.builder()
                .title(PRETEXT)
                .titleLink(TITLE_LINK)
                .email(receiverEmails)
                .content(MessageFormat.format(SENDER, senderName))
                .content(MessageFormat.format(COUPON, alarm.getContents().get(COUPON_INDEX)))
                .content(MessageFormat.format(DATE, alarm.getContents().get(DATE_INDEX)))
                .build();
    }

    @Override
    public AlarmType getAlarmType() {
        return AlarmType.RESERVATION_SENT;
    }
}

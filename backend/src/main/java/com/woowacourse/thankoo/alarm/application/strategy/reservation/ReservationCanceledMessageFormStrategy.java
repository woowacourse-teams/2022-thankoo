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
public class ReservationCanceledMessageFormStrategy extends ReservationMessageFormStrategy {

    private static final int CONTENT_SIZE = 2;
    private static final String PRETEXT_CANCEL = "\uD83D\uDE05 예약이 취소되었어요ㅜ";

    private final AlarmMemberProvider alarmMemberProvider;

    @Override
    public Message createFormat(final Alarm alarm) {
        validateContentSize(alarm, CONTENT_SIZE);
        List<String> receiverEmails = alarmMemberProvider.getReceiverEmails(alarm.getTargetIds());
        String senderName = alarmMemberProvider.getSenderName(alarm.getContents().get(SENDER_ID_INDEX));

        return Message.builder()
                .email(receiverEmails)
                .title(PRETEXT_CANCEL)
                .titleLink(TITLE_LINK)
                .content(MessageFormat.format(SENDER, senderName))
                .content(MessageFormat.format(COUPON, alarm.getContents().get(COUPON_INDEX)))
                .build();
    }

    @Override
    public AlarmType getAlarmType() {
        return AlarmType.RESERVATION_CANCEL;
    }
}

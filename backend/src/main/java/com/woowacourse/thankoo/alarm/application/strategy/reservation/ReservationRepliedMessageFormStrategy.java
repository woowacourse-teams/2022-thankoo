package com.woowacourse.thankoo.alarm.application.strategy.reservation;

import com.woowacourse.thankoo.alarm.application.dto.Message;
import com.woowacourse.thankoo.alarm.application.strategy.AlarmMemberProvider;
import com.woowacourse.thankoo.alarm.application.strategy.ReservationMessageFormStrategy;
import com.woowacourse.thankoo.alarm.domain.Alarm;
import com.woowacourse.thankoo.alarm.domain.AlarmType;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationRepliedMessageFormStrategy extends ReservationMessageFormStrategy {

    private static final int CONTENT_SIZE = 3;

    private static final String PRETEXT_RESPONSE = "\uD83D\uDC7B {0}님이 예약 요청에 응답했어요.";
    private static final int STATUS_INDEX = 2;
    private static final String RESERVATION_STATUS = "예약 상태 : {0}";
    private static final Map<String, String> statuses = Map.of(
            "ACCEPT", "승인\uD83E\uDD70",
            "DENY", "거절\uD83D\uDE05");

    private final AlarmMemberProvider alarmMemberProvider;

    @Override
    public Message createFormat(final Alarm alarm) {
        validateContentSize(alarm, CONTENT_SIZE);
        List<String> receiverEmails = alarmMemberProvider.getReceiverEmails(alarm.getTargetIds());
        String senderName = alarmMemberProvider.getSenderName(alarm.getContentAt(SENDER_ID_INDEX));
        return Message.builder()
                .title(MessageFormat.format(PRETEXT_RESPONSE, senderName))
                .titleLink(TITLE_LINK)
                .email(receiverEmails)
                .content(MessageFormat.format(COUPON, alarm.getContentAt(COUPON_INDEX)))
                .content(MessageFormat.format(RESERVATION_STATUS,
                        statuses.get(alarm.getContentAt(STATUS_INDEX))))
                .build();
    }

    @Override
    public AlarmType getAlarmType() {
        return AlarmType.RESERVATION_REPLIED;
    }
}

package com.woowacourse.thankoo.alarm.application.strategy;

import com.woowacourse.thankoo.alarm.application.MessageFormStrategy;
import com.woowacourse.thankoo.alarm.domain.Alarm;
import com.woowacourse.thankoo.alarm.exception.InvalidAlarmException;
import com.woowacourse.thankoo.common.exception.ErrorType;
import java.util.Map;

public abstract class ReservationMessageFormStrategy implements MessageFormStrategy {

    protected static final String PRETEXT_RESPONSE = "\uD83D\uDC7B 예약 요청에 응답이 왔어요.";
    protected static final String PRETEXT_CANCEL = "\uD83D\uDE05 예약이 취소되었어요ㅜ";
    protected static final String TITLE_LINK = "https://thankoo.co.kr/reservations";
    protected static final String SENDER = "요청자 : {0}";
    protected static final String RESERVATION_STATUS = "예약 상태 : {0}";
    protected static final String COUPON = "쿠폰 : {0}";

    protected static final int SENDER_ID_INDEX = 0;
    protected static final int COUPON_INDEX = 1;

    protected static final Map<String, String> type = Map.of(
            "ACCEPT", "승인\uD83E\uDD70",
            "DENY", "거절\uD83D\uDE05");

    protected void validateContentSize(final Alarm alarm, final int size) {
        if (!alarm.hasContentsSize(size)) {
            throw new InvalidAlarmException(ErrorType.INVALID_ALARM_FORMAT);
        }
    }
}

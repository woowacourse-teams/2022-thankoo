package com.woowacourse.thankoo.alarm.application.strategy;

import com.woowacourse.thankoo.alarm.application.MessageFormStrategy;
import com.woowacourse.thankoo.alarm.domain.Alarm;
import com.woowacourse.thankoo.alarm.exception.InvalidAlarmException;
import com.woowacourse.thankoo.common.exception.ErrorType;

public abstract class ReservationMessageFormStrategy implements MessageFormStrategy {

    protected static final String TITLE_LINK = "https://thankoo.co.kr/reservations";
    protected static final String SENDER = "요청자 : {0}";
    protected static final String COUPON = "쿠폰 : {0}";

    protected static final int SENDER_ID_INDEX = 0;
    protected static final int COUPON_INDEX = 1;

    protected void validateContentSize(final Alarm alarm, final int size) {
        if (!alarm.hasContentsSize(size)) {
            throw new InvalidAlarmException(ErrorType.INVALID_ALARM_FORMAT);
        }
    }
}

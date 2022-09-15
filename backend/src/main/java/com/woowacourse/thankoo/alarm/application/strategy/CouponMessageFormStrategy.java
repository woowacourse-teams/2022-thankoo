package com.woowacourse.thankoo.alarm.application.strategy;

import com.woowacourse.thankoo.alarm.application.MessageFormStrategy;
import com.woowacourse.thankoo.alarm.domain.Alarm;
import com.woowacourse.thankoo.alarm.exception.InvalidAlarmException;
import com.woowacourse.thankoo.common.exception.ErrorType;

public abstract class CouponMessageFormStrategy implements MessageFormStrategy {

    protected static final String SENDER = "보내는 이 : {0}";
    protected static final String TITLE = "제목 : {0}";
    protected static final String TITLE_LINK = "https://thankoo.co.kr";
    protected static final String TYPE = "쿠폰 종류 : {0}";

    protected static final int SENDER_ID_INDEX = 0;
    protected static final int TITLE_INDEX = 1;

    protected static final int CONTENT_SIZE = 3;

    protected void validateContent(final Alarm alarm) {
        if (!alarm.hasContentsSize(CONTENT_SIZE)) {
            throw new InvalidAlarmException(ErrorType.INVALID_ALARM_FORMAT);
        }
    }
}

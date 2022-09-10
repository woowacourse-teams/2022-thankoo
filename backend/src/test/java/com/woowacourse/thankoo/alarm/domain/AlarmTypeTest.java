package com.woowacourse.thankoo.alarm.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("AlarmType 은 ")
class AlarmTypeTest {

    @DisplayName("AlarmType 을 생성한다.")
    @ParameterizedTest
    @CsvSource(value = {"coupon_sent_coffee:COUPON_SENT_COFFEE", "coupon_sent_meal:COUPON_SENT_MEAL"}, delimiter = ':')
    void from(final String value, final AlarmType alarmType) {
        assertThat(AlarmType.from(value)).isEqualTo(alarmType);
    }
}

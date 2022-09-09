package com.woowacourse.thankoo.alarm.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("AlarmType 은 ")
class AlarmTypeTest {

    @DisplayName("AlarmType 을 생성한다.")
    @Test
    void from() {
        AlarmType alarmType = AlarmType.from("coupon_sent");

        assertThat(alarmType).isEqualTo(AlarmType.COUPON_SENT);
    }
}

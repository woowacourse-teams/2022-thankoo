package com.woowacourse.thankoo.heart.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.thankoo.common.domain.AlarmSpecification;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("HeartSentEvent 는 ")
class HeartSentEventTest {

    private static final Long ORGANIZATION_ID = 1L;

    @DisplayName("알람 스펙으로 변경한다.")
    @Test
    void toAlarmSpecification() {
        Heart heart = new Heart(1L, 1L, 1L, 2L, 1, true);

        HeartSentEvent heartSentEvent = HeartSentEvent.from(heart);
        AlarmSpecification alarmSpecification = heartSentEvent.toAlarmSpecification();

        assertAll(
                () -> assertThat(alarmSpecification.getAlarmType()).isEqualTo(AlarmSpecification.HEART_SENT),
                () -> assertThat(alarmSpecification.getOrganizationId()).isEqualTo(ORGANIZATION_ID),
                () -> assertThat(alarmSpecification.getTargetIds()).containsExactly(2L),
                () -> assertThat(alarmSpecification.getContents()).containsExactly("1", "1")
        );
    }
}

package com.woowacourse.thankoo.alarm.domain;

import static com.woowacourse.thankoo.common.domain.AlarmSpecification.COUPON_SENT_COFFEE;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.thankoo.common.domain.AlarmSpecification;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("AlarmTest 는 ")
class AlarmTest {

    private static final Long ORGANIZATION_ID = 1L;

    @DisplayName("컨텐츠 사이즈를 갖고 있다.")
    @ParameterizedTest
    @CsvSource(value = {"1:true", "2:false"}, delimiter = ':')
    void hasContentsSize(final int size, final boolean result) {
        Alarm alarm = Alarm.create(
                new AlarmSpecification(COUPON_SENT_COFFEE, ORGANIZATION_ID, List.of(1L), List.of("contents")));

        assertThat(alarm.hasContentsSize(size)).isEqualTo(result);
    }

    @DisplayName("알람 명세로 알람을 생성한다.")
    @Test
    void create() {
        Alarm alarm = Alarm.create(
                new AlarmSpecification(COUPON_SENT_COFFEE, ORGANIZATION_ID, List.of(1L), List.of("contents")));

        assertThat(alarm.getAlarmStatus()).isEqualTo(AlarmStatus.CREATED);
    }

    @DisplayName("인덱스 위치의 값을 가져온다.")
    @Test
    void getContentAt() {
        Alarm alarm = Alarm.create(
                new AlarmSpecification(COUPON_SENT_COFFEE, ORGANIZATION_ID, List.of(1L), List.of("contents")));

        assertThat(alarm.getContentAt(0)).isEqualTo("contents");
    }
}

package com.woowacourse.thankoo.alarm.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.thankoo.alarm.exception.InvalidAlarmException;
import com.woowacourse.thankoo.common.annotations.ApplicationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("AlarmLinkGenerator 는 ")
@ApplicationTest
class AlarmLinkGeneratorTest {

    @Autowired
    private AlarmLinkGenerator alarmLinkGenerator;

    @DisplayName("링크를 만들 때 ")
    @Nested
    class CreateLinkTest {

        @DisplayName("/가 앞에 있으면 성공한다.")
        @Test
        void createLink() {
            String url = alarmLinkGenerator.createUrl("/meetings");
            assertThat(url).isEqualTo("https://thankoo.co.kr/meetings");
        }

        @DisplayName("/가 앞에 없으면 실패한다.")
        @Test
        void createLinkFailed() {
            assertThatThrownBy(() -> alarmLinkGenerator.createUrl("meetings"))
                    .isInstanceOf(InvalidAlarmException.class)
                    .hasMessage("잘못된 알람 링크입니다.");
        }
    }
}

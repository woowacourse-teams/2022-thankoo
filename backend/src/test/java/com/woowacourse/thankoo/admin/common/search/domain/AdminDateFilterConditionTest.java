package com.woowacourse.thankoo.admin.common.search.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.thankoo.admin.common.search.exception.AdminInvalidDateFilterConditionException;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("AdminDateFilterCondition 는 ")
class AdminDateFilterConditionTest {

    @DisplayName("종료 날짜보다 시작 날짜가 더 빠른 경우 예외가 발생한다.")
    @Test
    void fromWithInvalidDate() {
        LocalDateTime startDateTime = LocalDateTime.now();
        LocalDateTime endDateTime = startDateTime.minusDays(1L);

        assertThatThrownBy(() -> new AdminDateFilterCondition(startDateTime, endDateTime))
                .isInstanceOf(AdminInvalidDateFilterConditionException.class)
                .hasMessage("날짜 검색 조건이 올바르지 않습니다.");
    }
}
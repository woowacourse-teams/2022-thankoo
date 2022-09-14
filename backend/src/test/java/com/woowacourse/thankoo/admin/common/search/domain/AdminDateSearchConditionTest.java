package com.woowacourse.thankoo.admin.common.search.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.thankoo.admin.common.search.exception.AdminInvalidDateSearchConditionException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("AdminDateSearchCondition 는 ")
class AdminDateSearchConditionTest {

    @DisplayName("종료 날짜보다 시작 날짜가 더 빠른 경우 예외가 발생한다.")
    @Test
    void fromWithInvalidDate() {
        LocalDateTime startDateTime = LocalDateTime.now();
        LocalDateTime endDateTime = startDateTime.minusDays(1L);

        assertThatThrownBy(() -> new AdminDateSearchCondition(startDateTime, endDateTime))
                .isInstanceOf(AdminInvalidDateSearchConditionException.class)
                .hasMessage("날짜 검색 조건이 올바르지 않습니다.");
    }

    @DisplayName("시작 날짜 0시부터 와 종료 날짜 23:59의 날짜 검색 조건을 생성한다.")
    @Test
    void of() {
        LocalDate startDate = LocalDate.now().minusDays(5L);
        LocalDate endDate = LocalDate.now();
        AdminDateSearchCondition dateSearchCondition = AdminDateSearchCondition.of(startDate, endDate);

        LocalDateTime startDateTime = startDate.atTime(0, 0);
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);

        assertAll(
                () -> assertThat(dateSearchCondition.getStartDateTime()).isEqualTo(startDateTime),
                () -> assertThat(dateSearchCondition.getEndDateTime()).isEqualTo(endDateTime)
        );
    }
}
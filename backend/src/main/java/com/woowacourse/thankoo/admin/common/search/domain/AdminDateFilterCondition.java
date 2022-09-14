package com.woowacourse.thankoo.admin.common.search.domain;

import com.woowacourse.thankoo.admin.common.exception.AdminErrorType;
import com.woowacourse.thankoo.admin.common.search.exception.AdminInvalidDateFilterConditionException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class AdminDateFilterCondition {

    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;

    public AdminDateFilterCondition(final LocalDateTime startDateTime, final LocalDateTime endDateTime) {
        validateDate(startDateTime, endDateTime);
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    private void validateDate(final LocalDateTime startDateTime, final LocalDateTime endDateTime) {
        if (startDateTime.isAfter(endDateTime)) {
            throw new AdminInvalidDateFilterConditionException(AdminErrorType.INVALID_DATE_FILTER_CONDITION);
        }
    }

    public static AdminDateFilterCondition of(final LocalDate startDate, final LocalDate endDate) {
        return new AdminDateFilterCondition(startDate.atStartOfDay(), endDate.plusDays(1L).atStartOfDay());
    }
}

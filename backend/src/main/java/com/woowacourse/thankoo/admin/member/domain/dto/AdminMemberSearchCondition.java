package com.woowacourse.thankoo.admin.member.domain.dto;

import com.woowacourse.thankoo.admin.common.exception.AdminErrorType;
import com.woowacourse.thankoo.admin.member.exception.AdminInvalidMemberSearchConditionException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.Getter;

@Getter
public class AdminMemberSearchCondition {

    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;

    private AdminMemberSearchCondition(final LocalDateTime startDateTime, final LocalDateTime endDateTime) {
        validateDate(startDateTime, endDateTime);
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    private void validateDate(final LocalDateTime startDateTime, final LocalDateTime endDateTime) {
        if (startDateTime.isAfter(endDateTime)) {
            throw new AdminInvalidMemberSearchConditionException(AdminErrorType.INVALID_MEMBER_SEARCH_CONDITION);
        }
    }

    public static AdminMemberSearchCondition of(final LocalDate startDate, final LocalDate endDate) {
        return new AdminMemberSearchCondition(startDate.atStartOfDay(), endDate.atTime(LocalTime.MAX));
    }
}

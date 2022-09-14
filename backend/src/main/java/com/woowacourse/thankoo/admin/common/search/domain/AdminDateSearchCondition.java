package com.woowacourse.thankoo.admin.common.search.domain;

import com.woowacourse.thankoo.admin.common.exception.AdminErrorType;
import com.woowacourse.thankoo.admin.common.search.exception.AdminInvalidDateSearchConditionException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import lombok.Getter;

@Getter
public class AdminDateSearchCondition {

    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;

    public AdminDateSearchCondition(final LocalDateTime startDateTime, final LocalDateTime endDateTime) {
        validateDate(startDateTime, endDateTime);
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    private void validateDate(final LocalDateTime startDateTime, final LocalDateTime endDateTime) {
        if (startDateTime.isAfter(endDateTime)) {
            throw new AdminInvalidDateSearchConditionException(AdminErrorType.INVALID_DATE_FILTER_CONDITION);
        }
    }

    public static AdminDateSearchCondition of(final LocalDate startDate, final LocalDate endDate) {
        return new AdminDateSearchCondition(startDate.atStartOfDay(), endDate.atTime(LocalTime.MAX));
    }

    public String getStartDateTimeStringValue() {
        return startDateTime.format(getDateTimeFormatter());
    }

    public String getEndDateTimeStringValue() {
        return endDateTime.format(getDateTimeFormatter());
    }

    private DateTimeFormatter getDateTimeFormatter() {
        return DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);
    }
}

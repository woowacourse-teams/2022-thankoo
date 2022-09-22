package com.woowacourse.thankoo.admin.member.exception;

import com.woowacourse.thankoo.admin.common.exception.AdminBadRequestException;
import com.woowacourse.thankoo.admin.common.exception.AdminErrorType;

public class AdminInvalidMemberSearchConditionException extends AdminBadRequestException {

    public AdminInvalidMemberSearchConditionException(final AdminErrorType errorType) {
        super(errorType);
    }
}

package com.woowacourse.thankoo.admin.common.search.exception;

import com.woowacourse.thankoo.admin.common.exception.AdminBadRequestException;
import com.woowacourse.thankoo.admin.common.exception.AdminErrorType;

public class AdminInvalidDateFilterConditionException extends AdminBadRequestException {

    public AdminInvalidDateFilterConditionException(final AdminErrorType errorType) {
        super(errorType);
    }
}

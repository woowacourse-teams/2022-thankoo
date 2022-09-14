package com.woowacourse.thankoo.admin.common.search.exception;

import com.woowacourse.thankoo.admin.common.exception.AdminBadRequestException;
import com.woowacourse.thankoo.admin.common.exception.AdminErrorType;

public class AdminInvalidDateSearchConditionException extends AdminBadRequestException {

    public AdminInvalidDateSearchConditionException(final AdminErrorType errorType) {
        super(errorType);
    }
}

package com.woowacourse.thankoo.admin.organization.exception;

import com.woowacourse.thankoo.admin.common.exception.AdminBadRequestException;
import com.woowacourse.thankoo.admin.common.exception.AdminErrorType;

public class AdminInvalidOrganizationException extends AdminBadRequestException {

    public AdminInvalidOrganizationException(final AdminErrorType errorType) {
        super(errorType);
    }
}

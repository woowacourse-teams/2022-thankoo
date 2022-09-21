package com.woowacourse.thankoo.admin.serial.excepion;

import com.woowacourse.thankoo.admin.common.exception.AdminBadRequestException;
import com.woowacourse.thankoo.admin.common.exception.AdminErrorType;

public class AdminInvalidCouponSerialException extends AdminBadRequestException {

    public AdminInvalidCouponSerialException(final AdminErrorType adminErrorType) {
        super(adminErrorType);
    }
}

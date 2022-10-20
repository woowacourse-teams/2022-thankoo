package com.woowacourse.thankoo.admin.authentication.presentation.dto;

import com.woowacourse.thankoo.common.logging.MaskingUtil;
import lombok.Getter;

@Getter
public class AdminSignInResponse {

    private final Long adminId;
    private final String accessToken;

    public AdminSignInResponse(final Long adminId, final String accessToken) {
        this.adminId = adminId;
        this.accessToken = accessToken;
    }

    @Override
    public String toString() {
        return "AdminLoginResponse{" +
                "adminId=" + adminId +
                ", accessToken='" + MaskingUtil.mask(accessToken, 3) + '\'' +
                '}';
    }
}

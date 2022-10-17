package com.woowacourse.thankoo.admin.common.exception.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class AdminErrorResponse {

    private String message;

    public AdminErrorResponse(final String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "AdminErrorResponse{" +
                "message='" + message + '\'' +
                '}';
    }
}

package com.woowacourse.thankoo.organization.application.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class OrganizationJoinRequest {

    private String code;

    public OrganizationJoinRequest(final String code) {
        this.code = code;
    }
}

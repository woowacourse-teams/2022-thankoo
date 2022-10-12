package com.woowacourse.thankoo.admin.organization.application.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AdminOrganizationCreationRequest {

    private String name;
    private int limitedSize;

    public AdminOrganizationCreationRequest(final String name, final int limitedSize) {
        this.name = name;
        this.limitedSize = limitedSize;
    }

    @Override
    public String toString() {
        return "AdminOrganizationCreationRequeest{" +
                "name='" + name + '\'' +
                ", limitedSize=" + limitedSize +
                '}';
    }
}

package com.woowacourse.thankoo.organization.presentation.dto;

import com.woowacourse.thankoo.organization.domain.SimpleOrganizationData;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SimpleOrganizationResponse {

    private Long organizationId;
    private String name;

    public SimpleOrganizationResponse(final Long organizationId, final String name) {
        this.organizationId = organizationId;
        this.name = name;
    }

    public static SimpleOrganizationResponse from(final SimpleOrganizationData simpleOrganizationData) {
        return new SimpleOrganizationResponse(simpleOrganizationData.getId(), simpleOrganizationData.getName());
    }

    @Override
    public String toString() {
        return "SimpleOrganizationResponse{" +
                "organizationId=" + organizationId +
                ", name='" + name + '\'' +
                '}';
    }
}

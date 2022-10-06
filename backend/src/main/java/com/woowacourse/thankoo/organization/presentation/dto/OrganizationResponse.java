package com.woowacourse.thankoo.organization.presentation.dto;

import com.woowacourse.thankoo.organization.domain.MemberOrganization;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class OrganizationResponse {

    private Long organizationId;
    private String organizationName;
    private String organizationCode;
    private int orderNumber;
    private boolean lastAccessed;

    public OrganizationResponse(final Long organizationId,
                                final String organizationName,
                                final String organizationCode,
                                final int orderNumber,
                                final boolean lastAccessed) {
        this.organizationId = organizationId;
        this.organizationName = organizationName;
        this.organizationCode = organizationCode;
        this.orderNumber = orderNumber;
        this.lastAccessed = lastAccessed;
    }

    public static OrganizationResponse from(final MemberOrganization memberOrganization) {
        return new OrganizationResponse(memberOrganization.getId(),
                memberOrganization.getOrganizationName(),
                memberOrganization.getOrganizationCode(),
                memberOrganization.getOrderNumber(),
                memberOrganization.isLastAccessed());
    }
}

package com.woowacourse.thankoo.organization.domain;

import lombok.Getter;

@Getter
public class MemberOrganization {

    private final Long id;
    private final String organizationName;
    private final String organizationCode;
    private final int orderNumber;
    private final boolean lastAccessed;

    public MemberOrganization(final Long id,
                              final String organizationName,
                              final String organizationCode,
                              final int orderNumber,
                              final boolean lastAccessed) {
        this.id = id;
        this.organizationName = organizationName;
        this.organizationCode = organizationCode;
        this.orderNumber = orderNumber;
        this.lastAccessed = lastAccessed;
    }
}

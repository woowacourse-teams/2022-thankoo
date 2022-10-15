package com.woowacourse.thankoo.admin.organization.presentaion.dto;

import com.woowacourse.thankoo.organization.domain.Organization;
import com.woowacourse.thankoo.organization.domain.OrganizationCode;
import com.woowacourse.thankoo.organization.domain.OrganizationName;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class AdminGetOrganizationResponse {

    private Long id;
    private String name;
    private String code;
    private int limitedSize;

    private AdminGetOrganizationResponse(final Long id, final String name, final String code, final int limitedSize) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.limitedSize = limitedSize;
    }

    public static AdminGetOrganizationResponse from(final Organization organization) {
        OrganizationName organizationName = organization.getName();
        OrganizationCode organizationCode = organization.getCode();
        return new AdminGetOrganizationResponse(organization.getId(),
                organizationName.getValue(),
                organizationCode.getValue(),
                organization.getLimitedSize());
    }

    @Override
    public String toString() {
        return "AdminGetOrganizationResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", limitedSize=" + limitedSize +
                '}';
    }
}

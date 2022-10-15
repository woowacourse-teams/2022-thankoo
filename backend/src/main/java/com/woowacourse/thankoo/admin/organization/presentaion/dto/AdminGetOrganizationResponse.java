package com.woowacourse.thankoo.admin.organization.presentaion.dto;

import com.woowacourse.thankoo.organization.domain.Organization;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class AdminGetOrganizationResponse {

    List<AdminOrganizationResponse> organizations;

    private AdminGetOrganizationResponse(List<AdminOrganizationResponse> organizations) {
        this.organizations = organizations;
    }

    public static AdminGetOrganizationResponse from(final List<Organization> organizations) {
        List<AdminOrganizationResponse> adminOrganizationResponses = organizations.stream()
                .map(AdminOrganizationResponse::from)
                .collect(Collectors.toList());
        return new AdminGetOrganizationResponse(adminOrganizationResponses);
    }

    @Override
    public String toString() {
        return "AdminGetOrganizationResponse{" +
                "organizations=" + organizations +
                '}';
    }
}

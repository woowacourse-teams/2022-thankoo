package com.woowacourse.thankoo.admin.organization.application;

import com.woowacourse.thankoo.admin.organization.application.dto.AdminOrganizationCreationRequest;
import com.woowacourse.thankoo.admin.organization.domain.AdminOrganizationRepository;
import com.woowacourse.thankoo.organization.domain.Organization;
import com.woowacourse.thankoo.organization.domain.OrganizationValidator;
import com.woowacourse.thankoo.organization.infrastructure.OrganizationCodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminOrganizationService {

    private final AdminOrganizationRepository adminOrganizationRepository;
    private final OrganizationValidator organizationValidator;

    public void createOrganization(final AdminOrganizationCreationRequest organizationCreationRequest) {
        String organizationName = organizationCreationRequest.getName();
        Organization newOrganization = Organization.create(organizationName,
                new OrganizationCodeGenerator(),
                organizationCreationRequest.getLimitedSize(),
                organizationValidator);
        adminOrganizationRepository.save(newOrganization);
    }
}

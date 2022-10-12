package com.woowacourse.thankoo.admin.organization.application;

import com.woowacourse.thankoo.admin.organization.application.dto.AdminOrganizationCreationRequeest;
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

    public void createOrganization(final AdminOrganizationCreationRequeest organizationCreationRequeest) {
        String organizationName = organizationCreationRequeest.getName();
        Organization newOrganization = Organization.create(organizationName,
                new OrganizationCodeGenerator(),
                organizationCreationRequeest.getLimitedSize(),
                organizationValidator);
        adminOrganizationRepository.save(newOrganization);
    }
}

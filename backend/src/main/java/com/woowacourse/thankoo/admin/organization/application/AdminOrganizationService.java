package com.woowacourse.thankoo.admin.organization.application;

import com.woowacourse.thankoo.admin.organization.application.dto.AdminGetOrganizationsRequest;
import com.woowacourse.thankoo.admin.organization.application.dto.AdminOrganizationCreationRequest;
import com.woowacourse.thankoo.admin.organization.domain.AdminOrganizationRepository;
import com.woowacourse.thankoo.admin.organization.presentaion.dto.AdminOrganizationResponse;
import com.woowacourse.thankoo.organization.domain.Organization;
import com.woowacourse.thankoo.organization.domain.OrganizationValidator;
import com.woowacourse.thankoo.organization.infrastructure.OrganizationCodeGenerator;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
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

    public List<AdminOrganizationResponse> getOrganizations(
            final AdminGetOrganizationsRequest getOrganizationsRequest) {
        LocalDateTime startDateTime = getOrganizationsRequest.getStartDateTime();
        LocalDateTime endDateTime = getOrganizationsRequest.getEndDateTime();
        List<Organization> organizations = adminOrganizationRepository.findAllByCreatedAtBetween(startDateTime,
                endDateTime);
        return organizations.stream()
                .map(AdminOrganizationResponse::from)
                .collect(Collectors.toList());
    }
}

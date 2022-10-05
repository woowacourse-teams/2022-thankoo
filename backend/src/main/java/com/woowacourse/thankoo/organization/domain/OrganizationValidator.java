package com.woowacourse.thankoo.organization.domain;

import com.woowacourse.thankoo.common.exception.ErrorType;
import com.woowacourse.thankoo.organization.exception.InvalidOrganizationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrganizationValidator {

    private final OrganizationRepository organizationRepository;

    public void validate(final Organization organization) {
        if (organizationRepository.existsByCode(organization.getCode())) {
            throw new InvalidOrganizationException(ErrorType.INVALID_ORGANIZATION_CODE);
        }

        if (organizationRepository.existsByName(organization.getName())) {
            throw new InvalidOrganizationException(ErrorType.INVALID_ORGANIZATION_NAME);
        }
    }
}

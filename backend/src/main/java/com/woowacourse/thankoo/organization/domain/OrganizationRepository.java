package com.woowacourse.thankoo.organization.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {

    boolean existsByCode(OrganizationCode code);

    boolean existsByName(OrganizationName name);
}

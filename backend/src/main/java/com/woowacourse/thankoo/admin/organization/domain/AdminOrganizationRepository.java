package com.woowacourse.thankoo.admin.organization.domain;

import com.woowacourse.thankoo.organization.domain.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminOrganizationRepository extends JpaRepository<Organization, Long> {

}

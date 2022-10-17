package com.woowacourse.thankoo.admin.organization.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.thankoo.common.annotations.RepositoryTest;
import com.woowacourse.thankoo.organization.domain.CodeGenerator;
import com.woowacourse.thankoo.organization.domain.Organization;
import com.woowacourse.thankoo.organization.domain.OrganizationRepository;
import com.woowacourse.thankoo.organization.domain.OrganizationValidator;
import com.woowacourse.thankoo.organization.infrastructure.OrganizationCodeGenerator;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("AdminOrganizationRepository 는 ")
@RepositoryTest
class AdminOrganizationRepositoryTest {

    @Autowired
    private AdminOrganizationRepository adminOrganizationRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @DisplayName("기간으로 Organzation 을 조회한다.")
    @Test
    void findWithMemberById() {
        OrganizationValidator validator = new OrganizationValidator(organizationRepository);
        CodeGenerator codeGenerator = new OrganizationCodeGenerator();

        LocalDateTime startDateTime = LocalDateTime.now();
        Organization organization = Organization.create("org", codeGenerator, 12, validator);
        adminOrganizationRepository.save(organization);
        LocalDateTime endDateTime = LocalDateTime.now();
        List<Organization> searchResults = adminOrganizationRepository.findAllByCreatedAtBetween(startDateTime,
                endDateTime);

        assertThat(searchResults).isEqualTo(List.of(organization));
    }
}

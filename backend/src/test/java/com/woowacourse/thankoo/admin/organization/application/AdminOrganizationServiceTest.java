package com.woowacourse.thankoo.admin.organization.application;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatNoException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import com.woowacourse.thankoo.admin.organization.application.dto.AdminOrganizationCreationRequeest;
import com.woowacourse.thankoo.admin.organization.domain.AdminOrganizationRepository;
import com.woowacourse.thankoo.common.annotations.ApplicationTest;
import com.woowacourse.thankoo.organization.domain.Organization;
import com.woowacourse.thankoo.organization.domain.OrganizationRepository;
import com.woowacourse.thankoo.organization.domain.OrganizationValidator;
import com.woowacourse.thankoo.organization.exception.InvalidOrganizationException;
import com.woowacourse.thankoo.organization.infrastructure.OrganizationCodeGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("AdminOrganizationService 는 ")
@ApplicationTest
class AdminOrganizationServiceTest {

    @Autowired
    private AdminOrganizationService adminOrganizationService;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private AdminOrganizationRepository adminOrganizationRepository;

    @DisplayName("조직을 생성할 때 ")
    @Nested
    class CouponCreationTest {

        @DisplayName("생성가능한 조직이면 생성한다.")
        @Test
        void createOrganizationSuccess() {
            AdminOrganizationCreationRequeest organizationCreationRequeest = new AdminOrganizationCreationRequeest(
                    "newOrg", 10);

            assertThatNoException()
                    .isThrownBy(() -> adminOrganizationService.createOrganization(organizationCreationRequeest));
        }

        @DisplayName("기존 조직과 이름이 중복되면 에러가 발생한다.")
        @Test
        void createOrganizationFailDueToNameDuplicated() {
            AdminOrganizationCreationRequeest organizationCreationRequest = new AdminOrganizationCreationRequeest(
                    "exsitOrg", 10);
            Organization organization = Organization.create("exsitOrg", new OrganizationCodeGenerator(), 10,
                    new OrganizationValidator(organizationRepository));
            adminOrganizationRepository.save(organization);

            assertThatThrownBy(() -> adminOrganizationService.createOrganization(organizationCreationRequest))
                    .isInstanceOf(InvalidOrganizationException.class)
                    .hasMessage("잘못된 조직 이름입니다.");
        }

        @DisplayName("조직 제한 인원을 초과하면 에러가 발생한다.")
        @Test
        void createOrganizationFailDueToOverStaffed() {
            AdminOrganizationCreationRequeest organizationCreationRequeest = new AdminOrganizationCreationRequeest(
                    "newOrg", 501);

            assertThatThrownBy(() -> adminOrganizationService.createOrganization(organizationCreationRequeest))
                    .isInstanceOf(InvalidOrganizationException.class)
                    .hasMessage("유효하지 않은 조직의 인원입니다.");
        }

    }
}

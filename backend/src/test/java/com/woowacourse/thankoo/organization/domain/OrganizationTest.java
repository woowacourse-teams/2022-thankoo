package com.woowacourse.thankoo.organization.domain;

import static com.woowacourse.thankoo.common.fixtures.OrganizationFixture.ORGANIZATION_NAME;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.thankoo.organization.infrastructure.OrganizationCodeGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Organization 은 ")
class OrganizationTest {

    @DisplayName("새 조직을 생성할 때 ")
    @Nested
    class CreateTest {

        @DisplayName("조직원이 0명이다.")
        @Test
        void organizationMemberEmpty() {
            Organization organization = Organization.create(ORGANIZATION_NAME, new OrganizationCodeGenerator());
            assertThat(organization.getOrganizationMembers().getValues()).isEmpty();
        }

        @DisplayName("코드가 8글자이다.")
        @Test
        void codeSize() {
            Organization organization = Organization.create(ORGANIZATION_NAME, new OrganizationCodeGenerator());
            assertThat(organization.getCode().getValue()).hasSize(8);
        }
    }

}

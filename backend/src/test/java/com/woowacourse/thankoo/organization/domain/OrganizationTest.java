package com.woowacourse.thankoo.organization.domain;

import static com.woowacourse.thankoo.common.fixtures.OrganizationFixture.ORGANIZATION_NAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

import com.woowacourse.thankoo.organization.exception.InvalidOrganizationException;
import com.woowacourse.thankoo.organization.infrastructure.OrganizationCodeGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;

@DisplayName("Organization 은 ")
class OrganizationTest {

    private OrganizationValidator validator;

    @BeforeEach
    void setUp() {
        validator = Mockito.mock(OrganizationValidator.class);
        doNothing().when(validator).validate(any(Organization.class));
    }

    @DisplayName("새 조직을 생성할 때 ")
    @Nested
    class CreateTest {

        @DisplayName("조직원이 0명이다.")
        @Test
        void organizationMemberEmpty() {
            Organization organization = Organization.create(ORGANIZATION_NAME, new OrganizationCodeGenerator(), 100,
                    validator);
            assertThat(organization.getOrganizationMembers().getValues()).isEmpty();
        }

        @DisplayName("코드가 8글자이다.")
        @Test
        void codeSize() {
            Organization organization = Organization.create(ORGANIZATION_NAME, new OrganizationCodeGenerator(), 100,
                    validator);
            assertThat(organization.getCode().getValue()).hasSize(8);
        }

        @DisplayName("조직 정원이 적절할 경우 성공한다.")
        @ParameterizedTest
        @ValueSource(ints = {10, 500})
        void limitedSizeSuccess(final int value) {
            assertDoesNotThrow(
                    () -> Organization.create(ORGANIZATION_NAME, new OrganizationCodeGenerator(), value, validator));
        }

        @DisplayName("조직 정원이 적절하지 않을 경우 실패한다.")
        @ParameterizedTest
        @ValueSource(ints = {9, 501})
        void limitedSizeFailed(final int value) {
            assertThatThrownBy(
                    () -> Organization.create(ORGANIZATION_NAME, new OrganizationCodeGenerator(), value, validator))
                    .isInstanceOf(InvalidOrganizationException.class)
                    .hasMessage("조직의 정원을 다시 설정하세요.");
        }
    }
}

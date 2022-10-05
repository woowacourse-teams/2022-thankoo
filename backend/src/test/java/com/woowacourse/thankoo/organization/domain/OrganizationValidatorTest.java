package com.woowacourse.thankoo.organization.domain;

import static com.woowacourse.thankoo.common.fixtures.OrganizationFixture.ORGANIZATION_WOOWACOURSE;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.woowacourse.thankoo.common.annotations.ApplicationTest;
import com.woowacourse.thankoo.organization.exception.InvalidOrganizationException;
import com.woowacourse.thankoo.organization.infrastructure.OrganizationCodeGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("OrganizationValidator 는 ")
@ApplicationTest
class OrganizationValidatorTest {

    @Autowired
    private OrganizationValidator organizationValidator;

    @Autowired
    private OrganizationRepository organizationRepository;

    private CodeGenerator codeGenerator;

    @BeforeEach
    void setUp() {
        codeGenerator = new OrganizationCodeGenerator();
    }

    @DisplayName("검증할 때 ")
    @Nested
    class ValidateTest {

        @DisplayName("코드, 이름중복이 없으면 통과한다.")
        @Test
        void success() {
            Organization organization1 = Organization.create(ORGANIZATION_WOOWACOURSE, codeGenerator, 10,
                    organizationValidator);
            organizationRepository.save(organization1);

            Organization organization2 = Organization.create(ORGANIZATION_WOOWACOURSE + "a", codeGenerator, 100,
                    organizationValidator);
            assertDoesNotThrow(() -> organizationValidator.validate(organization2));
        }

        @DisplayName("코드가 중복되면 예외가 발생한다.")
        @Test
        void duplicateCode() {
            Organization organization = Organization.create(ORGANIZATION_WOOWACOURSE, codeGenerator, 10,
                    organizationValidator);
            organizationRepository.save(organization);

            assertThatThrownBy(() -> organizationValidator.validate(organization))
                    .isInstanceOf(InvalidOrganizationException.class)
                    .hasMessage("잘못된 조직 코드입니다.");
        }

        @DisplayName("이름이 중복되면 예외가 발생한다.")
        @Test
        void duplicateName() {
            Organization organization1 = Organization.create(ORGANIZATION_WOOWACOURSE, codeGenerator, 10,
                    organizationValidator);
            organizationRepository.save(organization1);

            assertThatThrownBy(() -> Organization.create(ORGANIZATION_WOOWACOURSE, codeGenerator, 100, organizationValidator))
                    .isInstanceOf(InvalidOrganizationException.class)
                    .hasMessage("잘못된 조직 이름입니다.");
        }
    }
}

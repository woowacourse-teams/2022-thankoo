package com.woowacourse.thankoo.organization.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.thankoo.organization.exception.InvalidOrganizationException;
import com.woowacourse.thankoo.organization.infrastructure.FakeCodeGenerator;
import com.woowacourse.thankoo.organization.infrastructure.OrganizationCodeGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("OrganizationCode 는 ")
class OrganizationCodeTest {

    @DisplayName("코드를 생성할 때 ")
    @Nested
    class CreateTest {

        @DisplayName("8자가 아닐 경우 실패한다.")
        @Test
        void sizeFailed() {
            assertThatThrownBy(() -> OrganizationCode.create(new FakeCodeGenerator()))
                    .isInstanceOf(InvalidOrganizationException.class)
                    .hasMessage("잘못된 조직 코드입니다.");
        }

        @DisplayName("8자일 경우 성공한다.")
        @Test
        void sizeSuccess() {
            String rawCode = OrganizationCode.create(new OrganizationCodeGenerator()).getValue();
            assertThat(rawCode).hasSize(8);
        }
    }
}

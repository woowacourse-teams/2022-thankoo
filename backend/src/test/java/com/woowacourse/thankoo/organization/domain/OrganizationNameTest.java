package com.woowacourse.thankoo.organization.domain;

import static com.woowacourse.thankoo.common.fixtures.OrganizationFixture.ORGANIZATION_NAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.woowacourse.thankoo.organization.exception.InvalidOrganizationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("OrganizationName 은 ")
class OrganizationNameTest {

    @DisplayName("객체를 만들 때 ")
    @Nested
    class CreateTest {

        @DisplayName("이름이 기준에 맞으면 성공한다.")
        @Test
        void success() {
            assertDoesNotThrow(() -> new OrganizationName(ORGANIZATION_NAME));
        }

        @DisplayName("이름 기준에 맞지 않으면 실패한다.")
        @ParameterizedTest
        @ValueSource(strings = {"", " ", "우아한테크코스의땡쿠화이팅", "우테코땡쿠의BackendEngineer"})
        void sizeFailed(final String value) {
            assertThatThrownBy(() -> new OrganizationName(value))
                    .isInstanceOf(InvalidOrganizationException.class)
                    .hasMessage("잘못된 조직 이름입니다.");
        }

        @DisplayName("이름 양 끝의 공백을 제거한다.")
        @ParameterizedTest
        @ValueSource(strings = {" 우아한 테크 ", "우아한 테크 ", " 우아한 테크"})
        void strip(final String value) {
            OrganizationName organizationName = new OrganizationName(value);
            assertThat(organizationName.getValue()).isEqualTo("우아한 테크");
        }
    }
}

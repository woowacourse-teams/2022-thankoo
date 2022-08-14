package com.woowacourse.thankoo.member.domain;

import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.IMAGE_URL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_NAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.thankoo.member.exception.InvalidMemberException;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("Member 는 ")
class MemberTest {

    @DisplayName("올바르지 않은 이름으로 생성하면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {" ", "abcdef"})
    void createWithInvalidNameException(String name) {
        assertThatThrownBy(() -> new Member(name, HUNI_EMAIL, HUNI_SOCIAL_ID, IMAGE_URL))
                .isInstanceOf(InvalidMemberException.class)
                .hasMessage("올바르지 않은 이름입니다.");
    }

    @DisplayName("올바르지 않은 이메일로 생성하면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {" ", "abcdefghijkabcdefghijk1", "abc@abc"})
    void createWithInvalidEmailException(String email) {
        assertThatThrownBy(() -> new Member(HUNI_NAME, email, HUNI_SOCIAL_ID, IMAGE_URL))
                .isInstanceOf(InvalidMemberException.class)
                .hasMessage("올바르지 않은 이메일 형식입니다.");
    }

    @DisplayName("둘 중 동일한 id를 판별한다.")
    @Test
    void hasSameId() {
        Member member = new Member(1L, HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, IMAGE_URL);
        assertThat(member.hasSameId(List.of(1L, 2L))).isTrue();
    }

    @DisplayName("동일한 id를 판별한다.")
    @Test
    void isSameId() {
        Member member = new Member(1L, HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, IMAGE_URL);
        assertThat(member.isSameId(1L)).isTrue();
    }

    @Nested
    @DisplayName("이름을 변경할 때 ")
    class UpdateNameTest {

        @DisplayName("정상적인 이름으로 변경하면 변경에 성공한다.")
        @Test
        void updateName() {
            Member member = new Member(HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, IMAGE_URL);
            member.updateName(LALA_NAME);

            assertThat(member.getName().getValue()).isEqualTo(LALA_NAME);
        }

        @DisplayName("올바르지 않은 이름으로 변경하면 예외가 발생한다.")
        @ParameterizedTest
        @ValueSource(strings = {" ", "abcdefghijkabcdefghijk1"})
        void updateBlankNameException(String name) {
            Member member = new Member(HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, IMAGE_URL);
            assertThatThrownBy(() -> member.updateName(name))
                    .isInstanceOf(InvalidMemberException.class)
                    .hasMessage("올바르지 않은 이름입니다.");

        }
    }
}

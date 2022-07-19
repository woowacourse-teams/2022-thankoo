package com.woowacourse.thankoo.member.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.thankoo.member.exception.InvalidMemberException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("Email 은 ")
class EmailTest {

    @DisplayName("올바르지 않은 이메일 값으로 생성하면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {" ", "abcdefghijkabcdefghijk1", "abc@abc"})
    void createWithInvalidEmailException(String value) {
        assertThatThrownBy(() -> new Email(value))
                .isInstanceOf(InvalidMemberException.class)
                .hasMessage("올바르지 않은 이메일 형식입니다.");
    }
}

package com.woowacourse.thankoo.member.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.woowacourse.thankoo.member.exception.InvalidMemberException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("Email 은 ")
class EmailTest {

    @DisplayName("올바르지 않은 이메일 값으로 생성하면 예외가 발생한다.")
    @ParameterizedTest(name = "email : {0}")
    @ValueSource(strings = {" ", "abcdefghijkabcdefghijk1", "abc@abc"})
    void createWithInvalidEmailException(final String value) {
        assertThatThrownBy(() -> new Email(value))
                .isInstanceOf(InvalidMemberException.class)
                .hasMessage("올바르지 않은 이메일 형식입니다.");
    }

    @DisplayName("올바른 이메일은 성공한다.")
    @ParameterizedTest(name = "email : {0}")
    @ValueSource(strings = {"huni@huni.com", "huni@huni.co.kr", "huni_thankoo@huni.com", "huni.a@huni.com",
            "hu.ni.dev@huni.com"})
    void create(final String value) {
        assertDoesNotThrow(() -> new Email(value));
    }

    @DisplayName("동일한 이메일인지 확인한다. ")
    @Test
    void equals() {
        Email email = new Email("huni@huni.com");

        assertThat(email).isEqualTo(new Email("huni@huni.com"));
    }
}

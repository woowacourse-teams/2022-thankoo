package com.woowacourse.thankoo.member.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.thankoo.member.exception.InvalidMemberException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("Name 은 ")
class NameTest {

    @DisplayName("이름의 앞/뒤에 공백이 있을 경우 제거한다.")
    @Test
    void CouponContentMessageTrim() {
        Name name = new Name(" name ");
        assertThat(name.getValue().equals("name")).isTrue();
    }

    @DisplayName("올바르지 않은 이름이 들어올 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {" ", "abcdef"})
    void createNameException(String value) {
        assertThatThrownBy(() -> new Name(value))
                .isInstanceOf(InvalidMemberException.class)
                .hasMessage("올바르지 않은 이름입니다.");
    }
}

package com.woowacourse.thankoo.member.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.woowacourse.thankoo.member.exception.InvalidMemberException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("Name 은 ")
class NameTest {

    @DisplayName("이름의 앞/뒤에 공백이 있을 경우 제거한다.")
    @Test
    void couponContentMessageTrim() {
        Name name = new Name(" name ");

        assertThat(name.getValue()).isEqualTo("name");
    }

    @DisplayName("올바른 이름은 성공한다.")
    @ParameterizedTest(name = "name = {0}")
    @ValueSource(strings = {"a", "abcde"})
    void createName(String value) {
        assertDoesNotThrow(() -> new Name(value));
    }

    @DisplayName("이름에 공백이거나 유효하지 않은 길이인 경우 예외가 발생한다.")
    @ParameterizedTest(name = "name : {0}")
    @ValueSource(strings = {" ", "abcdef"})
    void createNameException(String value) {
        assertThatThrownBy(() -> new Name(value))
                .isInstanceOf(InvalidMemberException.class)
                .hasMessage("올바르지 않은 이름입니다.");
    }

    @DisplayName("이름이 동일한지 확인한다.")
    @Test
    void equals() {
        Name name = new Name("hoho");

        assertThat(name).isEqualTo(new Name("hoho"));
    }
}

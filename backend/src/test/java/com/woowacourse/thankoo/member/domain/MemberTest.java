package com.woowacourse.thankoo.member.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Member 는 ")
class MemberTest {

    @DisplayName("동일한 id 인지 확인한다.")
    @Test
    void isSameId() {
        Member member = new Member(1L, "hoho");
        assertThat(member.isSameId(1L)).isTrue();
    }
}

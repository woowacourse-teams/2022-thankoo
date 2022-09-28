package com.woowacourse.thankoo.admin.administrator.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Administrator 는 ")
class AdministratorTest {

    @DisplayName("패스워드가 일치하는 지를 판별할 수 있어야한다.")
    @Test
    void isSamePassword() {
        Administrator administrator = new Administrator("adminName", "password", AdministratorRole.ROOT);

        assertThat(administrator.isSamePassword("password")).isTrue();
    }
}

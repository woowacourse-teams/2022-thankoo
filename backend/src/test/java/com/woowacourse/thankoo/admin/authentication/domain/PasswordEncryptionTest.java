package com.woowacourse.thankoo.admin.authentication.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.thankoo.admin.authentication.infrastructure.PasswordHashEncryption;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("PasswordEncryption 은 ")
class PasswordEncryptionTest {

    @DisplayName("주어진 비밀번호를 암호화해야 한다.")
    @Test
    void encrypt() {
        PasswordEncryption passwordEncryption = new PasswordHashEncryption("csclxnexia70000000", 65536, 128);
        String encryptedPassword = passwordEncryption.encrypt("password");

        assertThat(encryptedPassword).isNotEqualTo("password");
    }
}

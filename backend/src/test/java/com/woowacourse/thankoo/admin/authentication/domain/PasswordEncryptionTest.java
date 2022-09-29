package com.woowacourse.thankoo.admin.authentication.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@DisplayName("PasswordEncryption 은 ")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class PasswordEncryptionTest {

    @Autowired
    PasswordEncryption passwordEncryption;

    @DisplayName("주어진 비밀번호를 암호화해야 한다.")
    @Test
    void encrypt() {
        String encryptedPassword = passwordEncryption.encrypt("password");

        assertThat(encryptedPassword).isNotEqualTo("password");
    }
}

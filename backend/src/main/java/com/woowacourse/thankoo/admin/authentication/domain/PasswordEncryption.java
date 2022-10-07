package com.woowacourse.thankoo.admin.authentication.domain;

public interface PasswordEncryption {

    String encrypt(String plainPassword);
}

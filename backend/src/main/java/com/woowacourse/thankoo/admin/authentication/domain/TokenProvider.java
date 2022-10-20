package com.woowacourse.thankoo.admin.authentication.domain;

public interface TokenProvider {

    String create(String payload);
}

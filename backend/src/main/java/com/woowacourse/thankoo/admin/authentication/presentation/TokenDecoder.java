package com.woowacourse.thankoo.admin.authentication.presentation;

public interface TokenDecoder {

    String decode(String token);
}

package com.woowacourse.thankoo.authentication.application.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SignUpRequest {

    private String name;

    public SignUpRequest(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "SignUpRequest{" +
                "name='" + name + '\'' +
                '}';
    }
}

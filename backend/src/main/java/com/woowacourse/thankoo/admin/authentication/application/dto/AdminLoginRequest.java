package com.woowacourse.thankoo.admin.authentication.application.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class AdminLoginRequest {

    private String name;
    private String password;

    public AdminLoginRequest(final String name, final String password) {
        this.name = name;
        this.password = password;
    }

    @Override
    public String toString() {
        return "AdminLoginRequest{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

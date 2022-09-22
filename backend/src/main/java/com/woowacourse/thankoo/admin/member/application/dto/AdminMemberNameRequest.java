package com.woowacourse.thankoo.admin.member.application.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AdminMemberNameRequest {

    private String name;

    public AdminMemberNameRequest(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "AdminMemberNameRequest{" +
                "name='" + name + '\'' +
                '}';
    }
}

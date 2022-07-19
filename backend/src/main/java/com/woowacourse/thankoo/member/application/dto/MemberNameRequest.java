package com.woowacourse.thankoo.member.application.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class MemberNameRequest {

    private String name;

    public MemberNameRequest(final String name) {
        this.name = name;
    }
}

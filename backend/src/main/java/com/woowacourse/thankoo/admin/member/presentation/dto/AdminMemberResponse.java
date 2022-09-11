package com.woowacourse.thankoo.admin.member.presentation.dto;

import com.woowacourse.thankoo.member.domain.Member;
import lombok.Getter;

@Getter
public class AdminMemberResponse {

    private final Long id;
    private final String name;
    private final String email;

    public AdminMemberResponse(final Long id, final String name, final String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public static AdminMemberResponse of(final Member member) {
        return new AdminMemberResponse(member.getId(), member.getName().getValue(), member.getEmail().getValue());
    }
}

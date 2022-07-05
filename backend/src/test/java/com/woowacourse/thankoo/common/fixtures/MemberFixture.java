package com.woowacourse.thankoo.common.fixtures;

import com.woowacourse.thankoo.member.domain.Member;

public class MemberFixture {

    public static final String HUNI = "Huni";
    public static final String LALA = "Lala";

    public static Member createMember(final Long id, final String name) {
        return new Member(id, name);
    }
}

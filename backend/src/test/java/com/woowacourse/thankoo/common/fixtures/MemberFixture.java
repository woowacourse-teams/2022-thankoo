package com.woowacourse.thankoo.common.fixtures;

import com.woowacourse.thankoo.member.domain.Member;

public class MemberFixture {

    public static final String HUNI_NAME = "Huni";
    public static final String LALA_NAME = "Lala";
    public static final String SKRR_NAME = "Skrr";
    public static final String HOHO_NAME = "Hoho";

    public static final Member HUNI = createMember(1L, HUNI_NAME);
    public static final Member LALA = createMember(2L, LALA_NAME);
    public static final Member SKRR = createMember(3L, SKRR_NAME);
    public static final Member HOHO = createMember(4L, HOHO_NAME);

    private static Member createMember(final Long id, final String name) {
        return new Member(id, name);
    }
}

package com.woowacourse.thankoo.heart.domain;

import com.woowacourse.thankoo.member.domain.Member;
import lombok.Getter;

@Getter
public class MemberHeart {

    private final Long heartId;
    private final Member sender;
    private final int count;
    private final boolean isFinal;

    public MemberHeart(final Long heartId, final Member sender, final int count, final boolean isFinal) {
        this.heartId = heartId;
        this.sender = sender;
        this.count = count;
        this.isFinal = isFinal;
    }
}

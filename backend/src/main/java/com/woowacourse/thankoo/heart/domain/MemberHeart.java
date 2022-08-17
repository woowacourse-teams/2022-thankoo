package com.woowacourse.thankoo.heart.domain;

import com.woowacourse.thankoo.member.domain.Member;
import lombok.Getter;

@Getter
public class MemberHeart {

    private final Long heartId;
    private final Member sender;
    private final int count;

    public MemberHeart(final Long heartId, final Member sender, final int count) {
        this.heartId = heartId;
        this.sender = sender;
        this.count = count;
    }
}

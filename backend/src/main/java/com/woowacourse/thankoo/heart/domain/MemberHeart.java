package com.woowacourse.thankoo.heart.domain;

import com.woowacourse.thankoo.member.domain.Member;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class MemberHeart {

    private final Long heartId;
    private final Member sender;
    private final int count;
    private final LocalDateTime modifiedAt;
    private final boolean last;

    public MemberHeart(final Long heartId, final Member sender, final int count, final LocalDateTime modifiedAt,
                       final boolean last) {
        this.heartId = heartId;
        this.sender = sender;
        this.count = count;
        this.modifiedAt = modifiedAt;
        this.last = last;
    }
}

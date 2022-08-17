package com.woowacourse.thankoo.heart.presentation.dto;

import com.woowacourse.thankoo.heart.domain.MemberHeart;
import com.woowacourse.thankoo.member.presentation.dto.MemberResponse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ReceivedHeartResponse {

    private Long heartId;
    private MemberResponse sender;
    private int count;

    private ReceivedHeartResponse(final Long heartId,
                                  final MemberResponse sender,
                                  final int count) {
        this.heartId = heartId;
        this.sender = sender;
        this.count = count;
    }

    public static ReceivedHeartResponse from(final MemberHeart memberHeart) {
        return new ReceivedHeartResponse(memberHeart.getHeartId(),
                MemberResponse.of(memberHeart.getSender()),
                memberHeart.getCount());
    }
}

package com.woowacourse.thankoo.heart.presentation.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class HeartRequest {

    private Long receiverId;

    public HeartRequest(final Long receiverId) {
        this.receiverId = receiverId;
    }
}

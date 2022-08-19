package com.woowacourse.thankoo.common.alert.sender;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class MessageRequest {

    private String text;

    public MessageRequest(final String text) {
        this.text = text;
    }
}

package com.woowacourse.thankoo.alarm.support;

import java.util.List;
import lombok.Getter;

@Getter
public class AlarmMessageRequest {

    private final String email;
    private final String title;
    private final List<String> detailMessages;

    public AlarmMessageRequest(final String email, final String title, final List<String> detailMessages) {
        this.email = email;
        this.title = title;
        this.detailMessages = detailMessages;
    }
}

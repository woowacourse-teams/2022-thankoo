package com.woowacourse.thankoo.common.alarm.cache;

import lombok.Getter;

@Getter
public class CachedSlackUser {

    private final String email;
    private final String slackUserId;

    public CachedSlackUser(final String email, final String slackUserId) {
        this.email = email;
        this.slackUserId = slackUserId;
    }
}

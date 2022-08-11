package com.woowacourse.thankoo.alarm.domain;

import lombok.Getter;

@Getter
public class SlackUser {

    private final String email;
    private final String slackUserId;

    public SlackUser(final String email, final String slackUserId) {
        this.email = email;
        this.slackUserId = slackUserId;
    }
}

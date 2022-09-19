package com.woowacourse.thankoo.common.alert;

import java.util.List;

public class SlackAlarmFailedEvent {

    private final String title;
    private final String titleLink;
    private final String email;
    private final List<String> contents;

    public SlackAlarmFailedEvent(final String title,
                                 final String titleLink,
                                 final String email,
                                 final List<String> contents) {
        this.title = title;
        this.titleLink = titleLink;
        this.email = email;
        this.contents = contents;
    }

    @Override
    public String toString() {
        return "SlackAlarmFailedEvent{" +
                "title='" + title + '\'' +
                ", titleLink='" + titleLink + '\'' +
                ", email='" + email + '\'' +
                ", contents=" + contents +
                '}';
    }
}

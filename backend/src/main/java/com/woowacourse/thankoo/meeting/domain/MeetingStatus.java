package com.woowacourse.thankoo.meeting.domain;

public enum MeetingStatus {

    ON_PROGRESS,
    FINISHED;

    public boolean isOnProgress() {
        return this == ON_PROGRESS;
    }
}

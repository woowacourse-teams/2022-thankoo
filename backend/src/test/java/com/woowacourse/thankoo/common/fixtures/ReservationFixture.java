package com.woowacourse.thankoo.common.fixtures;

import java.time.LocalDateTime;

public class ReservationFixture {

    public static final String ACCEPT = "accept";
    public static final String DENY = "deny";

    public static LocalDateTime time(final Long plusDays) {
        return LocalDateTime.now().plusDays(plusDays);
    }

    private ReservationFixture() {
    }
}

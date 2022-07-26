package com.woowacourse.thankoo.common.fake;

import com.woowacourse.thankoo.reservation.application.ReservedMeetingCreator;

public class FakeReservedMeetingCreator implements ReservedMeetingCreator {

    @Override
    public void create(final Long reservationId) {
        if (reservationId == null || reservationId <= 0L) {
            throw new IllegalArgumentException();
        }
    }
}

package com.woowacourse.thankoo.common.fake;

import com.woowacourse.thankoo.reservation.application.ReservedMeetingCreator;
import com.woowacourse.thankoo.reservation.domain.Reservation;

public class FakeReservedMeetingCreator implements ReservedMeetingCreator {

    @Override
    public void create(final Reservation reservation) {
        if (reservation == null) {
            throw new IllegalArgumentException();
        }
    }
}

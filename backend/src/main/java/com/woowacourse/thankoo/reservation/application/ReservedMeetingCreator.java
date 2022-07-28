package com.woowacourse.thankoo.reservation.application;

import com.woowacourse.thankoo.reservation.domain.Reservation;

public interface ReservedMeetingCreator {

    void create(Reservation reservation);
}

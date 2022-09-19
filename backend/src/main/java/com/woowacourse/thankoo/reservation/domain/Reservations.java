package com.woowacourse.thankoo.reservation.domain;

import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class Reservations {

    private final List<Reservation> reservations;

    public Reservations(final List<Reservation> reservations) {
        this.reservations = List.copyOf(reservations);
    }

    public List<Long> getIds() {
        return reservations.stream()
                .map(Reservation::getId)
                .collect(Collectors.toList());
    }

    public List<Long> getCouponIds() {
        return reservations.stream()
                .map(reservation -> reservation.getCoupon().getId())
                .collect(Collectors.toList());
    }
}

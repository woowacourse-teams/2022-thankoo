package com.woowacourse.thankoo.coupon.domain;

import java.util.List;
import java.util.stream.Collectors;

public class Coupons {

    private final List<Coupon> values;

    public Coupons(final List<Coupon> values) {
        this.values = List.copyOf(values);
    }

    public List<Long> getCouponIds() {
        return values.stream()
                .map(Coupon::getId)
                .collect(Collectors.toList());
    }

    public List<Long> getReceiverIds() {
        return values.stream()
                .map(Coupon::getReceiverId)
                .collect(Collectors.toList());
    }
}

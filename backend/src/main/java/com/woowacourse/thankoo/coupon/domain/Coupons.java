package com.woowacourse.thankoo.coupon.domain;

import java.util.List;
import java.util.stream.Collectors;

public class Coupons {

    private final List<Coupon> coupons;

    public Coupons(final List<Coupon> coupons) {
        this.coupons = List.copyOf(coupons);
    }

    public List<Long> getCouponIds() {
        return coupons.stream()
                .map(Coupon::getId)
                .collect(Collectors.toList());
    }

    public List<Long> getReceiverIds() {
        return coupons.stream()
                .map(Coupon::getReceiverId)
                .collect(Collectors.toList());
    }
}

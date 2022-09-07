package com.woowacourse.thankoo.coupon.domain;

import com.woowacourse.thankoo.common.event.Events;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class Coupons {

    private final List<Coupon> values;

    // TODO : sender id, coupon content 다르면 예외 처리
    public Coupons(final List<Coupon> values) {
        this.values = List.copyOf(values);
    }

    public static Coupons distribute(final List<Coupon> values) {
        Coupons coupons = new Coupons(values);
        Events.publish(CouponSentAlarmEvent.from(coupons));
        return coupons;
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

    public Long getSenderId() {
        return values.get(0).getSenderId();
    }

    public CouponContent getCouponContent() {
        return values.get(0).getCouponContent();
    }
}

package com.woowacourse.thankoo.coupon.domain;

import com.woowacourse.thankoo.common.event.Events;
import com.woowacourse.thankoo.common.exception.ErrorType;
import com.woowacourse.thankoo.coupon.exception.InvalidCouponException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class Coupons {

    private final List<Coupon> values;

    public Coupons(final List<Coupon> values) {
        validateCouponSize(values);
        this.values = List.copyOf(values);
    }

    private void validateCouponSize(final List<Coupon> values) {
        if (values.isEmpty()) {
            throw new InvalidCouponException(ErrorType.CAN_NOT_CREATE_COUPON_GROUP);
        }
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

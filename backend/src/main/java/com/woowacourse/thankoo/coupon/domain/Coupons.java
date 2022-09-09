package com.woowacourse.thankoo.coupon.domain;

import com.woowacourse.thankoo.common.event.Events;
import com.woowacourse.thankoo.common.exception.ErrorType;
import com.woowacourse.thankoo.coupon.exception.InvalidCouponException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class Coupons {

    private static final int REPRESENTATIVE_INDEX = 0;

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

    public Long getRepresentativeSenderId() {
        Long representativeSenderId = values.get(REPRESENTATIVE_INDEX).getSenderId();
        validateSameSender(representativeSenderId);
        return representativeSenderId;
    }

    private void validateSameSender(final Long representativeSenderId) {
        if (!isSameSenders(representativeSenderId)) {
            throw new InvalidCouponException(ErrorType.NOT_IN_SAME_COUPON_GROUP);
        }
    }

    private boolean isSameSenders(final Long representativeSenderId) {
        return values.stream()
                .allMatch(value -> value.isSender(representativeSenderId));
    }

    public CouponContent getRepresentativeCouponContent() {
        CouponContent representativeCouponContent = values.get(REPRESENTATIVE_INDEX).getCouponContent();
        validateSameCouponContents(representativeCouponContent);
        return representativeCouponContent;
    }

    private void validateSameCouponContents(final CouponContent representativeCouponContent) {
        if (!isSameCouponContents(representativeCouponContent)) {
            throw new InvalidCouponException(ErrorType.NOT_IN_SAME_COUPON_GROUP);
        }
    }

    private boolean isSameCouponContents(final CouponContent representativeCouponContent) {
        return values.stream()
                .allMatch(value -> value.isSameCouponContent(representativeCouponContent));
    }
}

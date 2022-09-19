package com.woowacourse.thankoo.serial.infrastructer;

import com.woowacourse.thankoo.common.exception.ErrorType;
import com.woowacourse.thankoo.coupon.domain.CouponContent;
import com.woowacourse.thankoo.coupon.exception.InvalidCouponContentException;
import com.woowacourse.thankoo.serial.domain.CouponContentFactory;
import com.woowacourse.thankoo.serial.domain.CouponSerialMember;
import java.text.MessageFormat;
import java.util.Arrays;
import lombok.Getter;

public class CouponSerialContentFactory implements CouponContentFactory {

    private static final String TITLE = "{0}가(이) 보내는 {1} 쿠폰";
    private static final String MESSAGE = "고생하셨습니다.";

    private final CouponSerialMember couponSerialMember;

    public CouponSerialContentFactory(final CouponSerialMember couponSerialMember) {
        this.couponSerialMember = couponSerialMember;
    }

    @Override
    public CouponContent create() {
        return toCouponContent(couponSerialMember.getSenderName(), couponSerialMember.getCouponType());
    }

    private CouponContent toCouponContent(final String memberName, final String couponType) {
        return new CouponContent(couponType, createTitle(memberName, couponType), MESSAGE);
    }

    private static String createTitle(final String memberName, final String couponType) {
        return MessageFormat.format(TITLE, memberName, getValue(couponType));
    }

    private static String getValue(final String couponType) {
        return Type.of(couponType);
    }

    @Getter
    private enum Type {
        COFFEE("COFFEE", "커피"),
        MEAL("MEAL", "식사");

        private final String couponType;
        private final String value;

        Type(final String couponType, final String value) {
            this.couponType = couponType;
            this.value = value;
        }

        public static String of(final String couponType) {
            return getType(couponType).getValue();
        }

        private static Type getType(final String couponType) {
            return Arrays.stream(Type.values())
                    .filter(it -> it.couponType.equals(couponType))
                    .findFirst()
                    .orElseThrow(() -> new InvalidCouponContentException(ErrorType.INVALID_COUPON_TYPE));
        }
    }
}

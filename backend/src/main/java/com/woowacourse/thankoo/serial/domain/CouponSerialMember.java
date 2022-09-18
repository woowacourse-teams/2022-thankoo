package com.woowacourse.thankoo.serial.domain;

import static com.woowacourse.thankoo.coupon.domain.CouponStatus.NOT_USED;

import com.woowacourse.thankoo.common.exception.ErrorType;
import com.woowacourse.thankoo.coupon.domain.Coupon;
import com.woowacourse.thankoo.coupon.domain.CouponContent;
import com.woowacourse.thankoo.coupon.domain.CouponType;
import com.woowacourse.thankoo.coupon.exception.InvalidCouponContentException;
import java.text.MessageFormat;
import java.util.Arrays;
import lombok.Getter;

@Getter
public class CouponSerialMember {

    private static final String TITLE = "{0}가(이) 보내는 {1} 쿠폰";
    private static final String MESSAGE = "고생하셨습니다.";

    private final Long id;
    private final String code;
    private final Long memberId;
    private final String memberName;
    private final CouponType couponType;

    public CouponSerialMember(final Long id,
                              final String code,
                              final Long memberId,
                              final String memberName,
                              final String couponType) {
        this.id = id;
        this.code = code;
        this.memberId = memberId;
        this.memberName = memberName;
        this.couponType = CouponType.of(couponType);
    }

    public Coupon createCoupon(final Long receiverId) {
        return new Coupon(memberId, receiverId, toCouponContent(memberName, couponType), NOT_USED);
    }

    private static CouponContent toCouponContent(final String memberName, final CouponType couponType) {
        return new CouponContent(couponType, createTitle(memberName, couponType), MESSAGE);
    }

    private static String createTitle(final String memberName, final CouponType couponType) {
        return MessageFormat.format(TITLE, memberName, getValue(couponType));
    }

    private static String getValue(final CouponType couponType) {
        return Type.toValue(couponType);
    }

    @Getter
    private enum Type {
        COFFEE(CouponType.COFFEE, "커피"),
        MEAL(CouponType.MEAL, "식사");

        private final CouponType couponType;
        private final String value;

        Type(final CouponType couponType, final String value) {
            this.couponType = couponType;
            this.value = value;
        }

        public static String toValue(final CouponType couponType) {
            return getType(couponType).getValue();
        }

        private static Type getType(final CouponType couponType) {
            return Arrays.stream(Type.values())
                    .filter(it -> it.couponType == couponType)
                    .findFirst()
                    .orElseThrow(() -> new InvalidCouponContentException(ErrorType.INVALID_COUPON_TYPE));
        }
    }
}

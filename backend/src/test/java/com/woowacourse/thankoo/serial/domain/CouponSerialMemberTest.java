package com.woowacourse.thankoo.serial.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.thankoo.coupon.domain.Coupon;
import com.woowacourse.thankoo.coupon.domain.CouponContent;
import com.woowacourse.thankoo.coupon.domain.CouponType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("CouponSerialMember 는 ")
class CouponSerialMemberTest {

    @DisplayName("코치가 보내는 쿠폰이 생성된다.")
    @Test
    void createCoachCouponContent() {
        CouponSerialMember couponSerialMember = new CouponSerialMember(1L, "1234", 1L, "브리",
                CouponType.COFFEE.getValue());
        Coupon coupon = couponSerialMember.createCoupon(1L);
        CouponContent couponContent = coupon.getCouponContent();

        assertAll(
                () -> assertThat(couponContent.getTitle()).isEqualTo("브리가(이) 보내는 커피 쿠폰"),
                () -> assertThat(couponContent.getMessage()).isEqualTo("고생하셨습니다.")
        );
    }
}

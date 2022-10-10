package com.woowacourse.thankoo.serial.domain;

import static com.woowacourse.thankoo.common.fixtures.SerialFixture.SERIAL_1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.thankoo.coupon.domain.Coupon;
import com.woowacourse.thankoo.coupon.domain.CouponContent;
import com.woowacourse.thankoo.coupon.domain.CouponStatus;
import com.woowacourse.thankoo.coupon.domain.CouponType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("CouponSerialMember 는 ")
class CouponSerialMemberTest {

    @DisplayName("코치가 보내는 쿠폰이 생성된다.")
    @Test
    void createCoachCouponContent() {
        CouponSerialMember couponSerialMember = new CouponSerialMember(1L, 1L, SERIAL_1, 1L, "브리",
                CouponType.COFFEE.getValue(), CouponSerialStatus.NOT_USED.name());
        Coupon coupon = couponSerialMember.createCoupon(2L,
                new CouponContent(CouponType.COFFEE, "브리가(이) 보내는 커피 쿠폰", "고생하셨습니다."));

        assertAll(
                () -> assertThat(coupon.getReceiverId()).isEqualTo(2L),
                () -> assertThat(coupon.getSenderId()).isEqualTo(1L),
                () -> assertThat(coupon.getCouponStatus()).isEqualTo(CouponStatus.NOT_USED)
        );
    }

    @DisplayName("만료된 시리얼인지 확인한다.")
    @Test
    void isUsed() {
        CouponSerialMember couponSerialMember = new CouponSerialMember(1L, 1L, SERIAL_1, 1L, "브리",
                CouponType.COFFEE.getValue(), CouponSerialStatus.USED.name());

        assertThat(couponSerialMember.isUsed()).isTrue();
    }
}

package com.woowacourse.thankoo.serial.domain;

import static com.woowacourse.thankoo.common.fixtures.SerialFixture.SERIAL_1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.thankoo.coupon.domain.CouponContent;
import com.woowacourse.thankoo.serial.infrastructer.CouponSerialContentFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("CouponSerialContent 는 ")
class CouponSerialContentFactoryTest {

    @DisplayName("CouponSerialMember 를 가지고 쿠폰 내용을 생성한다.")
    @Test
    void create() {
        CouponSerialMember couponSerialMember = new CouponSerialMember(1L, SERIAL_1, 2L, "브리", "COFFEE",
                CouponSerialStatus.USED.name());
        CouponSerialContentFactory couponSerialContentFactory = new CouponSerialContentFactory(couponSerialMember);

        CouponContent couponContent = couponSerialContentFactory.create();

        assertAll(
                () -> assertThat(couponContent.getTitle()).isEqualTo("브리가(이) 보내는 커피 쿠폰"),
                () -> assertThat(couponContent.getMessage()).isEqualTo("고생하셨습니다.")
        );
    }
}

package com.woowacourse.thankoo.serial.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.thankoo.coupon.domain.CouponContent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("CouponSerialContent 는 ")
class CouponSerialContentTest {

    @DisplayName("CouponSerialMember 를 가지고 쿠폰 내용을 생성한다.")
    @Test
    void create() {
        CouponSerialMember couponSerialMember = new CouponSerialMember(1L, "1234", 2L, "브리", "COFFEE");
        CouponSerialContent couponSerialContent = new CouponSerialContent(couponSerialMember);

        CouponContent couponContent = couponSerialContent.create();

        assertAll(
                () -> assertThat(couponContent.getTitle()).isEqualTo("브리가(이) 보내는 커피 쿠폰"),
                () -> assertThat(couponContent.getMessage()).isEqualTo("고생하셨습니다.")
        );
    }
}

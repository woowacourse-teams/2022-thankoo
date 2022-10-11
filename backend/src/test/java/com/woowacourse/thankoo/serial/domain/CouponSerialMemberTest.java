package com.woowacourse.thankoo.serial.domain;

import static com.woowacourse.thankoo.common.fixtures.SerialFixture.SERIAL_1;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.thankoo.coupon.domain.CouponType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("CouponSerialMember 는 ")
class CouponSerialMemberTest {

    @DisplayName("만료된 시리얼인지 확인한다.")
    @Test
    void isUsed() {
        CouponSerialMember couponSerialMember = new CouponSerialMember(1L, SERIAL_1, 1L, "브리",
                CouponType.COFFEE.getValue(), CouponSerialStatus.USED.name());

        assertThat(couponSerialMember.isUsed()).isTrue();
    }
}

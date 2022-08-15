package com.woowacourse.thankoo.coupon.domain;

import static com.woowacourse.thankoo.common.fixtures.CouponFixture.MESSAGE;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.TITLE;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Coupons 는 ")
class CouponsTest {

    @Test
    @DisplayName("쿠폰들에서 쿠폰의 id를 가져온다.")
    void getCouponIds() {
        Coupons coupons = givenCoupons();
        List<Long> couponIds = coupons.getCouponIds();

        assertThat(couponIds).containsExactly(1L, 2L, 3L);
    }

    @Test
    @DisplayName("쿠폰들에서 받은 회원의 id를 가져온다.")
    void getReceiverIds() {
        Coupons coupons = givenCoupons();
        List<Long> receiverIds = coupons.getReceiverIds();

        assertThat(receiverIds).containsExactly(1L, 2L, 3L);
    }

    private Coupons givenCoupons() {
        List<Coupon> coupons = new ArrayList<>();
        for (long id = 0; id < 3; id++) {
            coupons.add(new Coupon(id + 1, id + 1, id + 1, new CouponContent(CouponType.COFFEE, TITLE, MESSAGE),
                    CouponStatus.NOT_USED));
        }
        return new Coupons(coupons);
    }
}
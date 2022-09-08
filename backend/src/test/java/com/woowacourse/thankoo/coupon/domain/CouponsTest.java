package com.woowacourse.thankoo.coupon.domain;

import static com.woowacourse.thankoo.common.fixtures.CouponFixture.MESSAGE;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.TITLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.thankoo.coupon.exception.InvalidCouponException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Coupons 는 ")
class CouponsTest {

    @DisplayName("쿠폰들에서 쿠폰의 id를 가져온다.")
    @Test
    void getCouponIds() {
        Coupons coupons = givenCoupons();
        List<Long> couponIds = coupons.getCouponIds();

        assertThat(couponIds).containsExactly(1L, 2L, 3L);
    }

    @DisplayName("쿠폰들에서 받은 회원의 id를 가져온다.")
    @Test
    void getReceiverIds() {
        Coupons coupons = givenCoupons();
        List<Long> receiverIds = coupons.getReceiverIds();

        assertThat(receiverIds).containsExactly(2L, 3L, 4L);
    }

    @DisplayName("빈 리스트가 들어올 경우 예외가 발생한다.")
    @Test
    void validateCouponSize() {
        assertThatThrownBy(() -> new Coupons(Collections.emptyList()))
                .isInstanceOf(InvalidCouponException.class)
                .hasMessage("쿠폰 그룹을 생성할 수 없습니다.");
    }

    @DisplayName("쿠폰을 발급한다.")
    @Test
    void distribute() {
        List<Coupon> coupons = new ArrayList<>();
        for (long id = 1; id < 4; id++) {
            coupons.add(new Coupon(id, 1L, id + 1, new CouponContent(CouponType.COFFEE, TITLE, MESSAGE),
                    CouponStatus.NOT_USED));
        }
        Coupons distributedCoupons = Coupons.distribute(coupons);

        assertThat(distributedCoupons.getCouponIds()).containsExactly(1L, 2L, 3L);
    }

    private Coupons givenCoupons() {
        List<Coupon> coupons = new ArrayList<>();
        for (long id = 1; id < 4; id++) {
            coupons.add(new Coupon(id, 1L, id + 1, new CouponContent(CouponType.COFFEE, TITLE, MESSAGE),
                    CouponStatus.NOT_USED));
        }
        return new Coupons(coupons);
    }
}

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
import org.junit.jupiter.api.Nested;
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
        Coupons coupons = Coupons.distribute(createRawCoupons(1L));

        assertThat(coupons.getCouponIds()).containsExactly(1L, 2L, 3L);
    }

    @DisplayName("대표 Sender Id 를 가져올 때 ")
    @Nested
    class GetRepresentativeSenderIdTest {

        @DisplayName("모두 동일한 Sender 일 경우 대표 id를 가져온다.")
        @Test
        void getRepresentativeSenderIfSame() {
            Coupons coupons = Coupons.distribute(createRawCoupons(1L));

            assertThat(coupons.getRepresentativeSenderId()).isEqualTo(1L);
        }

        @DisplayName("Sender 가 다를 경우 대표 id를 가져오지 못한다.")
        @Test
        void getRepresentativeSenderIdDifferentSenderFailed() {
            List<Coupon> values = createRawCoupons(1L);
            values.add(new Coupon(4L, 2L, 3L, new CouponContent(CouponType.COFFEE, TITLE, MESSAGE),
                    CouponStatus.NOT_USED));

            Coupons coupons = new Coupons(values);
            assertThatThrownBy(coupons::getRepresentativeSenderId)
                    .isInstanceOf(InvalidCouponException.class)
                    .hasMessage("동일한 쿠폰 그룹이 아닙니다.");
        }
    }

    @DisplayName("대표 CouponContent 를 가져올 때 ")
    @Nested
    class GetRepresentativeCouponContentTest {

        @DisplayName("모두 동일한 CouponContent 일 경우 대표 Content 를 가져온다.")
        @Test
        void getRepresentativeCouponContentIfSame() {
            Coupons coupons = Coupons.distribute(createRawCoupons(1L));

            assertThat(coupons.getRepresentativeCouponContent())
                    .isEqualTo(new CouponContent(CouponType.COFFEE, TITLE, MESSAGE));
        }

        @DisplayName("CouponContent 가 다를 경우 대표 Content 를 가져오지 못한다.")
        @Test
        void getRepresentativeCouponContentDifferentCouponContentFailed() {
            List<Coupon> values = createRawCoupons(1L);
            values.add(new Coupon(4L, 2L, 3L, new CouponContent(CouponType.MEAL, TITLE, MESSAGE),
                    CouponStatus.NOT_USED));

            Coupons coupons = new Coupons(values);
            assertThatThrownBy(coupons::getRepresentativeCouponContent)
                    .isInstanceOf(InvalidCouponException.class)
                    .hasMessage("동일한 쿠폰 그룹이 아닙니다.");
        }
    }

    private Coupons givenCoupons() {
        List<Coupon> coupons = createRawCoupons(1L);
        return new Coupons(coupons);
    }

    private List<Coupon> createRawCoupons(final Long senderId) {
        List<Coupon> coupons = new ArrayList<>();
        for (long id = 1; id < 4; id++) {
            coupons.add(new Coupon(id, senderId, id + 1, new CouponContent(CouponType.COFFEE, TITLE, MESSAGE),
                    CouponStatus.NOT_USED));
        }
        return coupons;
    }
}

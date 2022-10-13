package com.woowacourse.thankoo.coupon.domain;

import static com.woowacourse.thankoo.common.fixtures.CouponFixture.MESSAGE;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.TITLE;
import static com.woowacourse.thankoo.coupon.domain.CouponStatus.NOT_USED;
import static com.woowacourse.thankoo.coupon.domain.CouponType.COFFEE;
import static com.woowacourse.thankoo.coupon.domain.CouponType.MEAL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.thankoo.coupon.exception.InvalidCouponException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Coupons 는 ")
class CouponsTest {

    private static final Long ORGANIZATION_ID = 1L;

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

    @DisplayName("쿠폰을 발급할 때 ")
    @Nested
    class DistributeTest {

        @DisplayName("정상적으로 여러 장의 쿠폰을 발급한다.")
        @Test
        void distributeCoupons() {
            Coupons coupons = Coupons.distribute(createRawCoupons());

            assertThat(coupons.getCouponIds()).containsExactly(1L, 2L, 3L);
        }

        @DisplayName("정상적으로 한 장의 쿠폰을 발급한다.")
        @Test
        void distributeCoupon() {
            Coupons coupons = Coupons.distribute(
                    List.of(
                            new Coupon(1L,
                                    ORGANIZATION_ID,
                                    1L,
                                    2L,
                                    new CouponContent(COFFEE, TITLE, MESSAGE),
                                    NOT_USED)
                    )
            );

            assertThat(coupons.getCouponIds()).containsExactly(1L);
        }

        @DisplayName("보내는 이가 다를 경우 실패한다.")
        @Test
        void distributeFailedDifferentSender() {
            List<Coupon> values = createRawCoupons();
            values.add(new Coupon(4L, ORGANIZATION_ID, 2L, 3L, new CouponContent(COFFEE, TITLE, MESSAGE), NOT_USED));

            assertThatThrownBy(() -> Coupons.distribute(values))
                    .isInstanceOf(InvalidCouponException.class)
                    .hasMessage("동일한 쿠폰 그룹이 아닙니다.");
        }

        @DisplayName("쿠폰 내용이 다를 경우 실패한다.")
        @Test
        void distributeFailedDifferentContent() {
            List<Coupon> values = createRawCoupons();
            values.add(new Coupon(4L, ORGANIZATION_ID, 2L, 3L, new CouponContent(MEAL, TITLE, MESSAGE), NOT_USED));

            assertThatThrownBy(() -> Coupons.distribute(values))
                    .isInstanceOf(InvalidCouponException.class)
                    .hasMessage("동일한 쿠폰 그룹이 아닙니다.");
        }

        @DisplayName("조직의 id가 1개가 아닌 경우 예외가 발생한다.")
        @Test
        void duplicateOrganizationIds() {
            List<Long> organizationIds = List.of(1L, 1L, 2L);
            List<Coupon> values = organizationIds.stream()
                    .map(organizationId -> new Coupon(organizationId, 1L, 2L, new CouponContent(COFFEE, TITLE, MESSAGE),
                            NOT_USED))
                    .collect(Collectors.toList());

            assertThatThrownBy(() -> Coupons.distribute(values))
                    .isInstanceOf(InvalidCouponException.class)
                    .hasMessage("다른 조직으로 생성할 수 없습니다.");
        }
    }

    @DisplayName("대표 Sender Id 를 가져올 때 ")
    @Nested
    class GetRepresentativeSenderIdTest {

        @DisplayName("모두 동일한 Sender 일 경우 대표 id를 가져온다.")
        @Test
        void getRepresentativeSenderIfSame() {
            Coupons coupons = Coupons.distribute(createRawCoupons());

            assertThat(coupons.getRepresentativeSenderId()).isEqualTo(1L);
        }

        @DisplayName("Sender 가 다를 경우 대표 id를 가져오지 못한다.")
        @Test
        void getRepresentativeSenderIdDifferentSenderFailed() {
            List<Coupon> values = createRawCoupons();
            values.add(new Coupon(4L, ORGANIZATION_ID, 2L, 3L, new CouponContent(COFFEE, TITLE, MESSAGE), NOT_USED));

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
            Coupons coupons = Coupons.distribute(createRawCoupons());

            assertThat(coupons.getRepresentativeCouponContent())
                    .isEqualTo(new CouponContent(COFFEE, TITLE, MESSAGE));
        }

        @DisplayName("CouponContent 가 다를 경우 대표 Content 를 가져오지 못한다.")
        @Test
        void getRepresentativeCouponContentDifferentCouponContentFailed() {
            List<Coupon> values = createRawCoupons();
            values.add(new Coupon(4L, ORGANIZATION_ID, 2L, 3L, new CouponContent(MEAL, TITLE, MESSAGE),
                    NOT_USED));

            Coupons coupons = new Coupons(values);
            assertThatThrownBy(coupons::getRepresentativeCouponContent)
                    .isInstanceOf(InvalidCouponException.class)
                    .hasMessage("동일한 쿠폰 그룹이 아닙니다.");
        }
    }

    @DisplayName("쿠폰들에서 조직의 id를 가져온다.")
    @Test
    void getOrganizationId() {
        Coupons coupons = givenCoupons();
        Long organizationId = coupons.getOrganizationId();

        assertThat(organizationId).isEqualTo(1L);
    }

    private Coupons givenCoupons() {
        List<Coupon> coupons = createRawCoupons();
        return new Coupons(coupons);
    }

    private List<Coupon> createRawCoupons() {
        List<Coupon> coupons = new ArrayList<>();
        for (long id = 1; id < 4; id++) {
            coupons.add(
                    new Coupon(id, ORGANIZATION_ID, 1L, id + 1, new CouponContent(COFFEE, TITLE, MESSAGE), NOT_USED));
        }
        return coupons;
    }
}

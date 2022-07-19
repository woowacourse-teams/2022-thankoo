package com.woowacourse.thankoo.reservation.application;

import static com.woowacourse.thankoo.common.fixtures.CouponFixture.MESSAGE;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.TITLE;
import static com.woowacourse.thankoo.coupon.domain.CouponStatus.NOT_USED;
import static com.woowacourse.thankoo.coupon.domain.CouponType.COFFEE;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.thankoo.coupon.domain.Coupon;
import com.woowacourse.thankoo.coupon.domain.CouponContent;
import com.woowacourse.thankoo.coupon.domain.CouponRepository;
import com.woowacourse.thankoo.coupon.exception.InvalidCouponException;
import com.woowacourse.thankoo.member.exception.InvalidMemberException;
import com.woowacourse.thankoo.reservation.application.dto.ReservationRequest;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@DisplayName("ReservationService 는 ")
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
class ReservationServiceTest {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private CouponRepository couponRepository;

    @DisplayName("예약시 쿠폰이 존재하는지 검증한다.")
    @Test
    void isExistedCoupon() {
        Long memberId = 1L;
        assertThatThrownBy(() -> reservationService.reserve(memberId,
                new ReservationRequest(3L, LocalDateTime.of(1999, 3, 28, 18, 30))))
                .isInstanceOf(InvalidCouponException.class)
                .hasMessage("존재하지 않는 쿠폰입니다.");
    }

    @DisplayName("예약을 보내는 회원과 쿠폰을 받은 회원이 같은지 검증한다.")
    @Test
    void isSameReceiver() {
        Long receiverId = 2L;
        Long invalidReceiverId = 3L;

        Coupon coupon = couponRepository.save(
                new Coupon(1L, receiverId, new CouponContent(COFFEE, TITLE, MESSAGE), NOT_USED));

        assertThatThrownBy(() -> reservationService.reserve(invalidReceiverId,
                new ReservationRequest(coupon.getId(), LocalDateTime.of(1999, 3, 28, 18, 30))))
                .isInstanceOf(InvalidMemberException.class)
                .hasMessage("존재하지 않는 회원입니다.");
    }
}
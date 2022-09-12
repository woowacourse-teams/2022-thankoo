package com.woowacourse.thankoo.admin.reservation.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.thankoo.admin.coupon.domain.AdminCouponRepository;
import com.woowacourse.thankoo.admin.reservation.application.dto.AdminReservationResponse;
import com.woowacourse.thankoo.common.annotations.RepositoryTest;
import com.woowacourse.thankoo.coupon.domain.Coupon;
import com.woowacourse.thankoo.coupon.domain.CouponContent;
import com.woowacourse.thankoo.coupon.domain.CouponStatus;
import com.woowacourse.thankoo.coupon.domain.CouponType;
import com.woowacourse.thankoo.reservation.domain.Reservation;
import com.woowacourse.thankoo.reservation.domain.ReservationStatus;
import com.woowacourse.thankoo.reservation.domain.TimeZoneType;
import java.time.LocalDateTime;
import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@DisplayName("AdminReservationRepository 는 ")
@RepositoryTest
class AdminReservationQueryRepositoryTest {

    private AdminReservationQueryRepository reservationQueryRepository;

    @Autowired
    private AdminReservationRepository reservationRepository;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private AdminCouponRepository couponRepository;

    @BeforeEach
    void setUp() {
        reservationQueryRepository = new AdminReservationQueryRepository(new NamedParameterJdbcTemplate(dataSource));
    }

    @DisplayName("모든 예약을 조회한다.")
    @Test
    void findAll() {
        Coupon coupon1 = saveCoupon();
        Coupon coupon2 = saveCoupon();
        Coupon coupon3 = saveCoupon();

        List.of(coupon1, coupon2, coupon3)
                .forEach(coupon -> saveReservation(coupon.getReceiverId(), coupon));

        List<AdminReservationResponse> result = reservationQueryRepository.findAll();

        assertThat(result).hasSize(3);
    }

    private Coupon saveCoupon() {
        return couponRepository.save(
                new Coupon(1L, 2L, new CouponContent(CouponType.COFFEE, "호호의 커피쿠폰", "커피드세요."), CouponStatus.NOT_USED));
    }

    private Reservation saveReservation(final Long memberId, final Coupon coupon) {
        return reservationRepository.save(
                Reservation.reserve(LocalDateTime.now().plusDays(1L),
                        TimeZoneType.ASIA_SEOUL,
                        ReservationStatus.WAITING,
                        memberId,
                        coupon));
    }
}
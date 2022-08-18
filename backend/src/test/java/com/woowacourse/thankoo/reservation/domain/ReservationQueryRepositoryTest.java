package com.woowacourse.thankoo.reservation.domain;

import static com.woowacourse.thankoo.common.fixtures.CouponFixture.MESSAGE;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.TITLE;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_IMAGE_URL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_SOCIAL_ID;
import static com.woowacourse.thankoo.coupon.domain.CouponStatus.NOT_USED;
import static com.woowacourse.thankoo.coupon.domain.CouponType.COFFEE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.thankoo.common.annotations.RepositoryTest;
import com.woowacourse.thankoo.coupon.domain.Coupon;
import com.woowacourse.thankoo.coupon.domain.CouponContent;
import com.woowacourse.thankoo.coupon.domain.CouponRepository;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import java.time.LocalDateTime;
import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@DisplayName("ReservationQueryRepository 는 ")
@RepositoryTest
class ReservationQueryRepositoryTest {

    private ReservationQueryRepository reservationQueryRepository;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CouponRepository couponRepository;

    @BeforeEach
    void setUp() {
        reservationQueryRepository = new ReservationQueryRepository(new NamedParameterJdbcTemplate(dataSource));
    }

    @DisplayName("받은 예약을 조회한다.")
    @Test
    void findReceivedReservations() {
        Member lala = memberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, SKRR_IMAGE_URL));
        Member skrr = memberRepository.save(new Member(SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, SKRR_IMAGE_URL));

        Coupon coupon1 = couponRepository.save(
                new Coupon(lala.getId(), skrr.getId(), new CouponContent(COFFEE, TITLE, MESSAGE), NOT_USED));
        Coupon coupon2 = couponRepository.save(
                new Coupon(lala.getId(), skrr.getId(), new CouponContent(COFFEE, TITLE, MESSAGE), NOT_USED));
        Coupon coupon3 = couponRepository.save(
                new Coupon(skrr.getId(), lala.getId(), new CouponContent(COFFEE, TITLE, MESSAGE), NOT_USED));

        LocalDateTime meetingDate = LocalDateTime.now().plusDays(1L);
        reservationRepository.save(
                Reservation.reserve(meetingDate, TimeZoneType.ASIA_SEOUL, ReservationStatus.WAITING, skrr.getId(),
                        coupon1));
        reservationRepository.save(
                Reservation.reserve(meetingDate, TimeZoneType.ASIA_SEOUL, ReservationStatus.WAITING, skrr.getId(),
                        coupon2));
        reservationRepository.save(
                Reservation.reserve(meetingDate, TimeZoneType.ASIA_SEOUL, ReservationStatus.WAITING, lala.getId(),
                        coupon3));

        List<ReservationCoupon> receivedReservations = reservationQueryRepository.findReceivedReservations(lala.getId(),
                LocalDateTime.now());

        assertAll(
                () -> assertThat(receivedReservations).hasSize(2),
                () -> assertThat(receivedReservations).extracting("memberName").containsOnly(SKRR_NAME)
        );
    }

    @DisplayName("보낸 예약을 조회한다.")
    @Test
    void findSentReservations() {
        Member lala = memberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, SKRR_IMAGE_URL));
        Member skrr = memberRepository.save(new Member(SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, SKRR_IMAGE_URL));

        Coupon coupon1 = couponRepository.save(
                new Coupon(lala.getId(), skrr.getId(), new CouponContent(COFFEE, TITLE, MESSAGE), NOT_USED));
        Coupon coupon2 = couponRepository.save(
                new Coupon(lala.getId(), skrr.getId(), new CouponContent(COFFEE, TITLE, MESSAGE), NOT_USED));
        Coupon coupon3 = couponRepository.save(
                new Coupon(skrr.getId(), lala.getId(), new CouponContent(COFFEE, TITLE, MESSAGE), NOT_USED));

        LocalDateTime meetingDate = LocalDateTime.now().plusDays(1L);
        reservationRepository.save(
                Reservation.reserve(meetingDate, TimeZoneType.ASIA_SEOUL, ReservationStatus.WAITING, skrr.getId(),
                        coupon1));
        reservationRepository.save(
                Reservation.reserve(meetingDate, TimeZoneType.ASIA_SEOUL, ReservationStatus.WAITING, skrr.getId(),
                        coupon2));
        reservationRepository.save(
                Reservation.reserve(meetingDate, TimeZoneType.ASIA_SEOUL, ReservationStatus.WAITING, lala.getId(),
                        coupon3));

        List<ReservationCoupon> sentReservations = reservationQueryRepository.findSentReservations(lala.getId(),
                LocalDateTime.now());

        assertAll(
                () -> assertThat(sentReservations).hasSize(1),
                () -> assertThat(sentReservations).extracting("memberName").containsOnly(SKRR_NAME)
        );
    }
}

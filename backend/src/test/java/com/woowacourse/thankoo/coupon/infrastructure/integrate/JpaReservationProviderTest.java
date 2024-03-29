package com.woowacourse.thankoo.coupon.infrastructure.integrate;

import static com.woowacourse.thankoo.common.fixtures.CouponFixture.MESSAGE;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.TITLE;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_IMAGE_URL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.OrganizationFixture.createDefaultOrganization;
import static com.woowacourse.thankoo.common.fixtures.ReservationFixture.time;
import static com.woowacourse.thankoo.coupon.domain.CouponStatus.NOT_USED;
import static com.woowacourse.thankoo.coupon.domain.CouponType.COFFEE;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.thankoo.common.annotations.ApplicationTest;
import com.woowacourse.thankoo.coupon.domain.Coupon;
import com.woowacourse.thankoo.coupon.domain.CouponContent;
import com.woowacourse.thankoo.coupon.domain.CouponRepository;
import com.woowacourse.thankoo.coupon.infrastructure.integrate.dto.ReservationResponse;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import com.woowacourse.thankoo.organization.domain.Organization;
import com.woowacourse.thankoo.organization.domain.OrganizationRepository;
import com.woowacourse.thankoo.organization.domain.OrganizationValidator;
import com.woowacourse.thankoo.reservation.domain.Reservation;
import com.woowacourse.thankoo.reservation.domain.ReservationRepository;
import com.woowacourse.thankoo.reservation.domain.ReservationStatus;
import com.woowacourse.thankoo.reservation.domain.TimeZoneType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("JpaReservationProvider 는 ")
@ApplicationTest
class JpaReservationProviderTest {

    @Autowired
    private JpaReservationProvider jpaReservationProvider;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private OrganizationValidator organizationValidator;

    @DisplayName("정보를 조회한다.")
    @Test
    void getTimeResponseReservation() {
        Member sender = memberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, SKRR_IMAGE_URL));
        Member receiver = memberRepository.save(new Member(SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, SKRR_IMAGE_URL));

        Organization organization = organizationRepository.save(createDefaultOrganization(organizationValidator));
        Coupon coupon = couponRepository.save(
                new Coupon(organization.getId(), sender.getId(), receiver.getId(),
                        new CouponContent(COFFEE, TITLE, MESSAGE), NOT_USED));
        Reservation reservation = reservationRepository.save(
                Reservation.reserve(time(1L),
                        TimeZoneType.ASIA_SEOUL,
                        ReservationStatus.WAITING,
                        receiver.getId(),
                        coupon));
        ReservationResponse reservationResponse = jpaReservationProvider.getWaitingReservation(coupon.getId());
        assertThat(reservationResponse.getTime().getMeetingTime()).isEqualTo(
                reservation.getTimeUnit().getTime());
    }
}

package com.woowacourse.thankoo.admin.reservation.domain;

import static com.woowacourse.thankoo.common.fixtures.CouponFixture.MESSAGE;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.TITLE;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_IMAGE_URL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_IMAGE_URL;
import static com.woowacourse.thankoo.common.fixtures.OrganizationFixture.createDefaultOrganization;
import static com.woowacourse.thankoo.coupon.domain.CouponStatus.NOT_USED;
import static com.woowacourse.thankoo.coupon.domain.CouponType.COFFEE;
import static com.woowacourse.thankoo.coupon.domain.CouponType.MEAL;
import static com.woowacourse.thankoo.reservation.domain.ReservationStatus.CANCELED;
import static com.woowacourse.thankoo.reservation.domain.ReservationStatus.WAITING;
import static com.woowacourse.thankoo.reservation.domain.TimeZoneType.ASIA_SEOUL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

import com.woowacourse.thankoo.admin.coupon.domain.AdminCouponRepository;
import com.woowacourse.thankoo.admin.member.domain.AdminMemberRepository;
import com.woowacourse.thankoo.admin.organization.domain.AdminOrganizationRepository;
import com.woowacourse.thankoo.common.annotations.RepositoryTest;
import com.woowacourse.thankoo.coupon.domain.Coupon;
import com.woowacourse.thankoo.coupon.domain.CouponContent;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.organization.domain.Organization;
import com.woowacourse.thankoo.organization.domain.OrganizationValidator;
import com.woowacourse.thankoo.reservation.domain.Reservation;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("AdminReservationRepository 는 ")
@RepositoryTest
class AdminReservationRepositoryTest {

    @Autowired
    private AdminCouponRepository adminCouponRepository;

    @Autowired
    private AdminMemberRepository adminMemberRepository;

    @Autowired
    private AdminReservationRepository adminReservationRepository;

    @Autowired
    private AdminOrganizationRepository adminOrganizationRepository;

    private OrganizationValidator organizationValidator;

    @BeforeEach
    void setUp() {
        organizationValidator = Mockito.mock(OrganizationValidator.class);
        doNothing().when(organizationValidator).validate(any(Organization.class));
    }

    @DisplayName("쿠폰의 예약 상태를 변경한다. (대기중 -> 취소)")
    @Test
    void updateReservationStatus() {
        Member sender = adminMemberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, SKRR_IMAGE_URL));
        Member receiver = adminMemberRepository.save(new Member(HOHO_NAME, HOHO_EMAIL, HOHO_SOCIAL_ID, HUNI_IMAGE_URL));

        Organization organization = adminOrganizationRepository.save(createDefaultOrganization(organizationValidator));

        Coupon coffeeCoupon = adminCouponRepository.save(
                new Coupon(organization.getId(), sender.getId(), receiver.getId(),
                        new CouponContent(COFFEE, TITLE, MESSAGE), NOT_USED));
        Coupon mealCoupon = adminCouponRepository.save(
                new Coupon(organization.getId(), sender.getId(), receiver.getId(),
                        new CouponContent(MEAL, TITLE, MESSAGE), NOT_USED));

        LocalDateTime localDateTime = LocalDateTime.now().plusDays(2L);

        Reservation coffeeReservation = adminReservationRepository.save(
                Reservation.reserve(localDateTime, ASIA_SEOUL, WAITING, receiver.getId(), coffeeCoupon));
        Reservation mealReservation = adminReservationRepository.save(
                Reservation.reserve(localDateTime, ASIA_SEOUL, WAITING, receiver.getId(), mealCoupon));

        adminReservationRepository.updateReservationStatus(WAITING, CANCELED, List.of(coffeeCoupon, mealCoupon));

        List<Reservation> reservations = adminReservationRepository.findAllById(
                List.of(coffeeReservation.getId(), mealReservation.getId()));

        assertAll(
                () -> assertThat(reservations).hasSize(2),
                () -> assertThat(reservations).extracting("reservationStatus").containsOnly(CANCELED)
        );
    }
}

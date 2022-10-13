package com.woowacourse.thankoo.reservation.application;

import static com.woowacourse.thankoo.common.fixtures.CouponFixture.MESSAGE;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.TITLE;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_IMAGE_URL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.OrganizationFixture.createDefaultOrganization;
import static com.woowacourse.thankoo.coupon.domain.CouponStatus.NOT_USED;
import static com.woowacourse.thankoo.coupon.domain.CouponType.COFFEE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.thankoo.common.annotations.ApplicationTest;
import com.woowacourse.thankoo.coupon.domain.Coupon;
import com.woowacourse.thankoo.coupon.domain.CouponContent;
import com.woowacourse.thankoo.coupon.domain.CouponRepository;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import com.woowacourse.thankoo.organization.application.OrganizationService;
import com.woowacourse.thankoo.organization.application.dto.OrganizationJoinRequest;
import com.woowacourse.thankoo.organization.domain.Organization;
import com.woowacourse.thankoo.organization.domain.OrganizationRepository;
import com.woowacourse.thankoo.organization.domain.OrganizationValidator;
import com.woowacourse.thankoo.reservation.application.dto.ReservationRequest;
import com.woowacourse.thankoo.reservation.presentation.dto.SimpleReservationResponse;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("ReservationQueryService 는 ")
@ApplicationTest
class ReservationQueryServiceTest {

    @Autowired
    private ReservationQueryService reservationQueryService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private OrganizationValidator organizationValidator;

    @DisplayName("받은 예약을 조회한다.")
    @Test
    void getReceivedReservations() {
        Member lala = memberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, SKRR_IMAGE_URL));
        Member skrr = memberRepository.save(new Member(SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, SKRR_IMAGE_URL));
        Member huni = memberRepository.save(new Member(HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, SKRR_IMAGE_URL));

        Organization organization = organizationRepository.save(createDefaultOrganization(organizationValidator));
        join(organization.getCode().getValue(), lala.getId(), skrr.getId(), huni.getId());

        Coupon coupon1 = couponRepository.save(
                new Coupon(organization.getId(), lala.getId(), skrr.getId(), new CouponContent(COFFEE, TITLE, MESSAGE),
                        NOT_USED));
        Coupon coupon2 = couponRepository.save(
                new Coupon(organization.getId(), lala.getId(), skrr.getId(), new CouponContent(COFFEE, TITLE, MESSAGE),
                        NOT_USED));
        Coupon coupon3 = couponRepository.save(
                new Coupon(organization.getId(), skrr.getId(), lala.getId(), new CouponContent(COFFEE, TITLE, MESSAGE),
                        NOT_USED));
        Coupon coupon4 = couponRepository.save(
                new Coupon(organization.getId(), huni.getId(), lala.getId(), new CouponContent(COFFEE, TITLE, MESSAGE),
                        NOT_USED));

        reservationService.save(skrr.getId(),
                new ReservationRequest(coupon1.getId(), LocalDateTime.now().plusDays(1L)));
        reservationService.save(skrr.getId(),
                new ReservationRequest(coupon2.getId(), LocalDateTime.now().plusDays(1L)));
        reservationService.save(lala.getId(),
                new ReservationRequest(coupon3.getId(), LocalDateTime.now().plusDays(1L)));
        reservationService.save(lala.getId(),
                new ReservationRequest(coupon4.getId(), LocalDateTime.now().plusDays(1L)));

        List<SimpleReservationResponse> reservations = reservationQueryService.getReceivedReservations(lala.getId(),
                organization.getId());

        assertAll(
                () -> assertThat(reservations).hasSize(2),
                () -> assertThat(reservations).extracting("memberName").containsOnly(SKRR_NAME)
        );
    }

    @DisplayName("보낸 예약을 조회한다.")
    @Test
    void getSentReservations() {
        Member lala = memberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, SKRR_IMAGE_URL));
        Member skrr = memberRepository.save(new Member(SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, SKRR_IMAGE_URL));
        Member huni = memberRepository.save(new Member(HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, SKRR_IMAGE_URL));

        Organization organization = organizationRepository.save(createDefaultOrganization(organizationValidator));
        join(organization.getCode().getValue(), lala.getId(), skrr.getId());

        Coupon coupon1 = couponRepository.save(
                new Coupon(organization.getId(), lala.getId(), skrr.getId(), new CouponContent(COFFEE, TITLE, MESSAGE),
                        NOT_USED));
        Coupon coupon2 = couponRepository.save(
                new Coupon(organization.getId(), lala.getId(), skrr.getId(), new CouponContent(COFFEE, TITLE, MESSAGE),
                        NOT_USED));
        Coupon coupon3 = couponRepository.save(
                new Coupon(organization.getId(), skrr.getId(), lala.getId(), new CouponContent(COFFEE, TITLE, MESSAGE),
                        NOT_USED));
        Coupon coupon4 = couponRepository.save(
                new Coupon(organization.getId(), lala.getId(), huni.getId(), new CouponContent(COFFEE, TITLE, MESSAGE),
                        NOT_USED));

        reservationService.save(skrr.getId(),
                new ReservationRequest(coupon1.getId(), LocalDateTime.now().plusDays(1L)));
        reservationService.save(skrr.getId(),
                new ReservationRequest(coupon2.getId(), LocalDateTime.now().plusDays(1L)));
        reservationService.save(lala.getId(),
                new ReservationRequest(coupon3.getId(), LocalDateTime.now().plusDays(1L)));
        reservationService.save(huni.getId(),
                new ReservationRequest(coupon4.getId(), LocalDateTime.now().plusDays(1L)));

        List<SimpleReservationResponse> reservations = reservationQueryService.getSentReservations(lala.getId(),
                organization.getId());

        assertAll(
                () -> assertThat(reservations).hasSize(1),
                () -> assertThat(reservations).extracting("memberName").containsOnly(SKRR_NAME)
        );
    }

    private void join(final String code, final Long... memberIds) {
        for (Long memberId : memberIds) {
            organizationService.join(memberId, new OrganizationJoinRequest(code));
        }
    }
}

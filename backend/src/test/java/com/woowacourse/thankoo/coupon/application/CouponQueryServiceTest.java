package com.woowacourse.thankoo.coupon.application;

import static com.woowacourse.thankoo.common.fixtures.CouponFixture.ALL;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.MESSAGE;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.NOT_USED;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.TITLE;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.TYPE;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.USED;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.IMAGE_URL_SKRR;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_SOCIAL_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.thankoo.common.annotations.ApplicationTest;
import com.woowacourse.thankoo.common.dto.TimeResponse;
import com.woowacourse.thankoo.coupon.application.dto.ContentRequest;
import com.woowacourse.thankoo.coupon.application.dto.CouponRequest;
import com.woowacourse.thankoo.coupon.domain.Coupon;
import com.woowacourse.thankoo.coupon.domain.CouponContent;
import com.woowacourse.thankoo.coupon.domain.CouponRepository;
import com.woowacourse.thankoo.coupon.domain.CouponStatus;
import com.woowacourse.thankoo.coupon.presentation.dto.CouponDetailResponse;
import com.woowacourse.thankoo.coupon.presentation.dto.CouponResponse;
import com.woowacourse.thankoo.coupon.presentation.dto.CouponTotalResponse;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import com.woowacourse.thankoo.member.exception.InvalidMemberException;
import com.woowacourse.thankoo.reservation.application.ReservationService;
import com.woowacourse.thankoo.reservation.application.dto.ReservationRequest;
import com.woowacourse.thankoo.reservation.application.dto.ReservationStatusRequest;
import com.woowacourse.thankoo.reservation.domain.TimeZoneType;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("CouponQueryService 는 ")
@ApplicationTest
class CouponQueryServiceTest {

    @Autowired
    private CouponQueryService couponQueryService;

    @Autowired
    private CouponService couponService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("받은 쿠폰 중 사용하지 않은 쿠폰을 조회한다.")
    @Test
    void getReceivedCoupons() {
        Member sender = memberRepository.save(new Member(HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, IMAGE_URL_SKRR));
        Member receiver = memberRepository.save(new Member(SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, IMAGE_URL_SKRR));

        couponRepository.saveAll(List.of(
                new Coupon(sender.getId(), receiver.getId(), new CouponContent(TYPE, TITLE, MESSAGE),
                        CouponStatus.EXPIRED),
                new Coupon(sender.getId(), receiver.getId(), new CouponContent(TYPE, TITLE, MESSAGE),
                        CouponStatus.RESERVED),
                new Coupon(sender.getId(), receiver.getId(), new CouponContent(TYPE, TITLE, MESSAGE),
                        CouponStatus.NOT_USED)
        ));

        List<CouponResponse> responses = couponQueryService.getReceivedCoupons(receiver.getId(), NOT_USED);

        assertAll(
                () -> assertThat(responses).hasSize(2),
                () -> assertThat(responses.get(0).getSender().getId()).isEqualTo(sender.getId()),
                () -> assertThat(responses.get(0).getReceiver().getId()).isEqualTo(receiver.getId())
        );
    }

    @DisplayName("받은 쿠폰 중 사용한 쿠폰을 조회한다.")
    @Test
    void getReceivedCouponsUsed() {
        Member sender = memberRepository.save(new Member(HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, IMAGE_URL));
        Member receiver = memberRepository.save(new Member(SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, IMAGE_URL));

        couponRepository.saveAll(List.of(
                new Coupon(sender.getId(), receiver.getId(), new CouponContent(TYPE, TITLE, MESSAGE),
                        CouponStatus.EXPIRED),
                new Coupon(sender.getId(), receiver.getId(), new CouponContent(TYPE, TITLE, MESSAGE),
                        CouponStatus.RESERVED),
                new Coupon(sender.getId(), receiver.getId(), new CouponContent(TYPE, TITLE, MESSAGE),
                        CouponStatus.NOT_USED)
        ));

        List<CouponResponse> responses = couponQueryService.getReceivedCoupons(receiver.getId(), USED);

        assertAll(
                () -> assertThat(responses).hasSize(1),
                () -> assertThat(responses.get(0).getSender().getId()).isEqualTo(sender.getId()),
                () -> assertThat(responses.get(0).getReceiver().getId()).isEqualTo(receiver.getId())
        );
    }

    @DisplayName("모든 받은 쿠폰을 조회한다.")
    @Test
    void getReceivedCouponsAll() {
        Member sender = memberRepository.save(new Member(HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, IMAGE_URL));
        Member receiver = memberRepository.save(new Member(SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, IMAGE_URL));

        couponRepository.saveAll(List.of(
                new Coupon(sender.getId(), receiver.getId(), new CouponContent(TYPE, TITLE, MESSAGE),
                        CouponStatus.EXPIRED),
                new Coupon(sender.getId(), receiver.getId(), new CouponContent(TYPE, TITLE, MESSAGE),
                        CouponStatus.RESERVED),
                new Coupon(sender.getId(), receiver.getId(), new CouponContent(TYPE, TITLE, MESSAGE),
                        CouponStatus.NOT_USED)
        ));

        List<CouponResponse> responses = couponQueryService.getReceivedCoupons(receiver.getId(), ALL);

        assertAll(
                () -> assertThat(responses).hasSize(3),
                () -> assertThat(responses.get(0).getSender().getId()).isEqualTo(sender.getId()),
                () -> assertThat(responses.get(0).getReceiver().getId()).isEqualTo(receiver.getId())
        );
    }

    @DisplayName("보낸 쿠폰을 조회한다.")
    @Test
    void getSentCoupons() {
        Member sender = memberRepository.save(new Member(HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, IMAGE_URL_SKRR));
        Member receiver = memberRepository.save(new Member(SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, IMAGE_URL_SKRR));

        couponService.saveAll(sender.getId(), new CouponRequest(List.of(receiver.getId()),
                new ContentRequest(TYPE, TITLE, MESSAGE)));

        List<CouponResponse> responses = couponQueryService.getSentCoupons(sender.getId());

        assertAll(
                () -> assertThat(responses).hasSize(1),
                () -> assertThat(responses.get(0).getSender().getId()).isEqualTo(sender.getId()),
                () -> assertThat(responses.get(0).getReceiver().getId()).isEqualTo(receiver.getId())
        );
    }

        Member sender = memberRepository.save(new Member(HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, IMAGE_URL));
        Member receiver = memberRepository.save(new Member(HOHO_NAME, HOHO_EMAIL, HOHO_SOCIAL_ID, IMAGE_URL));

        couponRepository.saveAll(List.of(
                new Coupon(sender.getId(), receiver.getId(), new CouponContent(TYPE, TITLE, MESSAGE),
                        CouponStatus.NOT_USED),
                new Coupon(sender.getId(), receiver.getId(), new CouponContent(TYPE, TITLE, MESSAGE),
                        CouponStatus.RESERVED),
                new Coupon(sender.getId(), receiver.getId(), new CouponContent(TYPE, TITLE, MESSAGE),
                        CouponStatus.USED),
                new Coupon(receiver.getId(), sender.getId(), new CouponContent(TYPE, TITLE, MESSAGE),
                        CouponStatus.RESERVED),
                new Coupon(receiver.getId(), sender.getId(), new CouponContent(TYPE, TITLE, MESSAGE),
                        CouponStatus.USED)
        ));

        CouponTotalResponse couponTotal = couponQueryService.getCouponTotalCount(sender.getId());
        assertAll(
                () -> assertThat(couponTotal.getSentCount()).isEqualTo(3),
                () -> assertThat(couponTotal.getReceivedCount()).isEqualTo(2)
        );
    }

    @DisplayName("단일 쿠폰을 조회할 때 ")
    @Nested
    class CouponDetailTest {

        @DisplayName("멤버가 쿠폰의 주인이 아니면 예외가 발생한다.")
        @Test
        void invalidMember() {
            Member sender = memberRepository.save(new Member(HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, IMAGE_URL_SKRR));
            Member receiver = memberRepository.save(new Member(SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, IMAGE_URL_SKRR));
            Member other = memberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, IMAGE_URL_SKRR));

            Coupon coupon = couponRepository.save(new Coupon(sender.getId(),
                    receiver.getId(),
                    new CouponContent(TYPE, TITLE, MESSAGE),
                    CouponStatus.NOT_USED));

            assertThatThrownBy(() -> couponQueryService.getCouponDetail(other.getId(), coupon.getId()))
                    .isInstanceOf(InvalidMemberException.class)
                    .hasMessage("올바르지 않은 회원입니다.");
        }

        @DisplayName("쿠폰 상태가 예약 중일 때 예약 정보를 함께 조회한다.")
        @Test
        void getCouponWithReserving() {
            Member sender = memberRepository.save(new Member(HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, IMAGE_URL_SKRR));
            Member receiver = memberRepository.save(new Member(SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, IMAGE_URL_SKRR));

            Coupon coupon = couponRepository.save(new Coupon(sender.getId(),
                    receiver.getId(),
                    new CouponContent(TYPE, TITLE, MESSAGE),
                    CouponStatus.NOT_USED));

            TimeResponse timeResponse = TimeResponse.from(LocalDateTime.now().plusDays(1L),
                    TimeZoneType.ASIA_SEOUL.getId());

            Long reservationId = reservationService.save(receiver.getId(),
                    new ReservationRequest(coupon.getId(), timeResponse.getMeetingTime()));

            CouponDetailResponse couponDetailResponse = couponQueryService.getCouponDetail(receiver.getId(),
                    coupon.getId());

            assertThat(couponDetailResponse.getReservation()
                    .getReservationId())
                    .isEqualTo(reservationId);
        }

        @DisplayName("쿠폰 상태가 예약 됨일 때 미팅 정보를 함께 조회한다.")
        @Test
        void getCouponWithMeeting() {
            Member sender = memberRepository.save(new Member(HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, IMAGE_URL_SKRR));
            Member receiver = memberRepository.save(new Member(SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, IMAGE_URL_SKRR));

            Coupon coupon = couponRepository.save(new Coupon(sender.getId(),
                    receiver.getId(),
                    new CouponContent(TYPE, TITLE, MESSAGE),
                    CouponStatus.NOT_USED));

            TimeResponse timeResponse = TimeResponse.from(LocalDateTime.now().plusDays(1L),
                    TimeZoneType.ASIA_SEOUL.getId());

            Long reservationId = reservationService.save(receiver.getId(),
                    new ReservationRequest(coupon.getId(), timeResponse.getMeetingTime()));
            reservationService.updateStatus(sender.getId(), reservationId, new ReservationStatusRequest("accept"));

            CouponDetailResponse couponDetailResponse = couponQueryService.getCouponDetail(receiver.getId(),
                    coupon.getId());
            assertThat(couponDetailResponse.getMeeting()).isNotNull();
        }

        @DisplayName("쿠폰 상태가 사용되지 않았을 때 쿠폰만 조회한다.")
        @Test
        void getCouponNoTime() {
            Member sender = memberRepository.save(new Member(HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, IMAGE_URL_SKRR));
            Member receiver = memberRepository.save(new Member(SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, IMAGE_URL_SKRR));

            Coupon coupon = couponRepository.save(new Coupon(sender.getId(),
                    receiver.getId(),
                    new CouponContent(TYPE, TITLE, MESSAGE),
                    CouponStatus.NOT_USED));

            CouponDetailResponse couponDetailResponse = couponQueryService.getCouponDetail(receiver.getId(),
                    coupon.getId());
            assertAll(
                    () -> assertThat(couponDetailResponse.getReservation()).isNull(),
                    () -> assertThat(couponDetailResponse.getMeeting()).isNull()
            );
        }
    }
}

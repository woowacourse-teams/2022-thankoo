package com.woowacourse.thankoo.meeting.domain;

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
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

import com.woowacourse.thankoo.common.annotations.RepositoryTest;
import com.woowacourse.thankoo.coupon.domain.Coupon;
import com.woowacourse.thankoo.coupon.domain.CouponContent;
import com.woowacourse.thankoo.coupon.domain.CouponRepository;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import com.woowacourse.thankoo.organization.domain.Organization;
import com.woowacourse.thankoo.organization.domain.OrganizationRepository;
import com.woowacourse.thankoo.organization.domain.OrganizationValidator;
import com.woowacourse.thankoo.reservation.domain.Reservation;
import com.woowacourse.thankoo.reservation.domain.ReservationRepository;
import com.woowacourse.thankoo.reservation.domain.ReservationStatus;
import com.woowacourse.thankoo.reservation.domain.TimeZoneType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("MeetingRepository 는 ")
@RepositoryTest
class MeetingRepositoryTest {

    @Autowired
    private MeetingRepository meetingRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    private OrganizationValidator organizationValidator;

    @BeforeEach
    void setUp() {
        organizationValidator = Mockito.mock(OrganizationValidator.class);
        doNothing().when(organizationValidator).validate(any(Organization.class));
    }


    @DisplayName("쿠폰으로 미팅을 조회한다.")
    @Test
    void findMeetingByCoupon() {
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
        Meeting savedMeeting = meetingRepository.save(
                new Meeting(
                        List.of(sender, receiver),
                        reservation.getTimeUnit(),
                        MeetingStatus.ON_PROGRESS,
                        coupon)
        );
        Meeting foundMeeting = meetingRepository.findTopByCouponId(coupon.getId()).get();

        assertThat(foundMeeting).isEqualTo(savedMeeting);
    }

    @DisplayName("미팅의 상태와 날짜로 미팅을 모두 조회한다.")
    @Test
    void findAllByMeetingStatusAndMeetingTimeDate() {
        LocalDateTime dateTime = LocalDateTime.now().plusDays(1L);

        Member sender = memberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, SKRR_IMAGE_URL));
        Member receiver = memberRepository.save(new Member(SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, SKRR_IMAGE_URL));

        Organization organization = organizationRepository.save(createDefaultOrganization(organizationValidator));

        for (int i = 0; i < 3; i++) {
            Coupon coupon = couponRepository.save(
                    new Coupon(organization.getId(), sender.getId(), receiver.getId(),
                            new CouponContent(COFFEE, TITLE, MESSAGE), NOT_USED));
            Reservation reservation = reservationRepository.save(
                    Reservation.reserve(dateTime,
                            TimeZoneType.ASIA_SEOUL,
                            ReservationStatus.WAITING,
                            receiver.getId(),
                            coupon));
            meetingRepository.save(new Meeting(
                    List.of(sender, receiver),
                    reservation.getTimeUnit(),
                    MeetingStatus.ON_PROGRESS,
                    coupon)
            );
        }

        List<Meeting> meetings = meetingRepository.findAllByMeetingStatusAndTimeUnitTime(
                MeetingStatus.ON_PROGRESS,
                dateTime);

        assertThat(meetings).hasSize(3);
    }

    @DisplayName("미팅의 상태와 날짜로 미팅을 모두 조회한다. (다른 날짜가 있는 경우)")
    @Test
    void findAllByMeetingStatusAndMeetingTimeDateWithOtherDate() {
        LocalDateTime dateTime1 = LocalDateTime.now().plusDays(1L);
        LocalDateTime dateTime2 = LocalDateTime.now().plusDays(3L);

        Member sender = memberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, SKRR_IMAGE_URL));
        Member receiver = memberRepository.save(new Member(SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, SKRR_IMAGE_URL));

        Organization organization = organizationRepository.save(createDefaultOrganization(organizationValidator));

        for (int i = 0; i < 3; i++) {
            Coupon coupon = couponRepository.save(
                    new Coupon(organization.getId(), sender.getId(), receiver.getId(),
                            new CouponContent(COFFEE, TITLE, MESSAGE), NOT_USED));
            Reservation reservation = reservationRepository.save(
                    Reservation.reserve(dateTime1,
                            TimeZoneType.ASIA_SEOUL,
                            ReservationStatus.WAITING,
                            receiver.getId(),
                            coupon));
            meetingRepository.save(new Meeting(
                    List.of(sender, receiver),
                    reservation.getTimeUnit(),
                    MeetingStatus.ON_PROGRESS,
                    coupon)
            );
        }

        Coupon coupon1 = couponRepository.save(
                new Coupon(organization.getId(), sender.getId(), receiver.getId(),
                        new CouponContent(COFFEE, TITLE, MESSAGE), NOT_USED));
        Reservation reservation1 = reservationRepository.save(
                Reservation.reserve(dateTime2,
                        TimeZoneType.ASIA_SEOUL,
                        ReservationStatus.WAITING,
                        receiver.getId(),
                        coupon1));
        meetingRepository.save(new Meeting(
                List.of(sender, receiver),
                reservation1.getTimeUnit(),
                MeetingStatus.ON_PROGRESS,
                coupon1)
        );

        List<Meeting> meetings = meetingRepository.findAllByMeetingStatusAndTimeUnitTime(
                MeetingStatus.ON_PROGRESS,
                dateTime1);

        assertThat(meetings).hasSize(3);
    }

    @DisplayName("만남의 상태와 미팅 id로 미팅 상태를 변경한다.")
    @Test
    void updateMeetingStatus() {
        Member sender = memberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, SKRR_IMAGE_URL));
        Member receiver = memberRepository.save(new Member(SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, SKRR_IMAGE_URL));

        Organization organization = organizationRepository.save(createDefaultOrganization(organizationValidator));

        List<Long> meetingIds = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Coupon coupon = couponRepository.save(
                    new Coupon(organization.getId(), sender.getId(), receiver.getId(),
                            new CouponContent(COFFEE, TITLE, MESSAGE), NOT_USED));
            Reservation reservation = reservationRepository.save(
                    Reservation.reserve(time(1L),
                            TimeZoneType.ASIA_SEOUL,
                            ReservationStatus.WAITING,
                            receiver.getId(),
                            coupon));
            Meeting meeting = meetingRepository.save(new Meeting(
                    List.of(sender, receiver),
                    reservation.getTimeUnit(),
                    MeetingStatus.ON_PROGRESS,
                    coupon));
            meetingIds.add(meeting.getId());
        }

        meetingRepository.updateMeetingStatus(MeetingStatus.FINISHED, meetingIds);

        List<Meeting> meetings = meetingRepository.findAll();

        assertAll(
                () -> assertThat(meetings).hasSize(3),
                () -> assertThat(meetings).extracting("meetingStatus").containsOnly(MeetingStatus.FINISHED)
        );
    }

    @DisplayName("해당하는 날의 미팅을 모두 조회한다.")
    @Test
    void findAllByTimeUnitDate() {
        Member sender = memberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, SKRR_IMAGE_URL));
        Member receiver = memberRepository.save(new Member(SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, SKRR_IMAGE_URL));

        Organization organization = organizationRepository.save(createDefaultOrganization(organizationValidator));

        for (int i = 0; i < 3; i++) {
            Coupon coupon = couponRepository.save(
                    new Coupon(organization.getId(), sender.getId(), receiver.getId(),
                            new CouponContent(COFFEE, TITLE, MESSAGE), NOT_USED));
            Reservation reservation = reservationRepository.save(
                    Reservation.reserve(time(1L),
                            TimeZoneType.ASIA_SEOUL,
                            ReservationStatus.WAITING,
                            receiver.getId(),
                            coupon));
            meetingRepository.save(new Meeting(
                    List.of(sender, receiver),
                    reservation.getTimeUnit(),
                    MeetingStatus.ON_PROGRESS,
                    coupon)
            );
        }

        List<Meeting> meetings = meetingRepository.findAllByTimeUnitDate(LocalDate.now().plusDays(1L));

        assertThat(meetings).hasSize(3);
    }
}

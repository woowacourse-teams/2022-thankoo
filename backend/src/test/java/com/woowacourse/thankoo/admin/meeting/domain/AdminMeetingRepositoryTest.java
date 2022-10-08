package com.woowacourse.thankoo.admin.meeting.domain;

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
import static com.woowacourse.thankoo.meeting.domain.MeetingStatus.FINISHED;
import static com.woowacourse.thankoo.meeting.domain.MeetingStatus.ON_PROGRESS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

import com.woowacourse.thankoo.admin.coupon.domain.AdminCouponRepository;
import com.woowacourse.thankoo.admin.member.domain.AdminMemberRepository;
import com.woowacourse.thankoo.admin.organization.domain.AdminOrganizationRepository;
import com.woowacourse.thankoo.common.annotations.RepositoryTest;
import com.woowacourse.thankoo.common.domain.TimeUnit;
import com.woowacourse.thankoo.coupon.domain.Coupon;
import com.woowacourse.thankoo.coupon.domain.CouponContent;
import com.woowacourse.thankoo.meeting.domain.Meeting;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.organization.domain.Organization;
import com.woowacourse.thankoo.organization.domain.OrganizationValidator;
import com.woowacourse.thankoo.reservation.domain.TimeZoneType;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("AdminMeetingRepository 는 ")
@RepositoryTest
class AdminMeetingRepositoryTest {

    @Autowired
    private AdminCouponRepository adminCouponRepository;

    @Autowired
    private AdminMemberRepository adminMemberRepository;

    @Autowired
    private AdminMeetingRepository adminMeetingRepository;

    @Autowired
    private AdminOrganizationRepository adminOrganizationRepository;

    private OrganizationValidator organizationValidator;

    @BeforeEach
    void setUp() {
        organizationValidator = Mockito.mock(OrganizationValidator.class);
        doNothing().when(organizationValidator).validate(any(Organization.class));
    }

    @DisplayName("쿠폰의 만남 상태를 변경한다. (진행 중 -> 완료)")
    @Test
    void updateMeetingStatus() {
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
        TimeUnit timeUnit = new TimeUnit(localDateTime.toLocalDate(), localDateTime, TimeZoneType.ASIA_SEOUL.getId());

        Meeting coffeeMeeting = adminMeetingRepository.save(
                new Meeting(List.of(sender, receiver), timeUnit, ON_PROGRESS, coffeeCoupon));
        Meeting mealMeeting = adminMeetingRepository.save(
                new Meeting(List.of(sender, receiver), timeUnit, ON_PROGRESS, mealCoupon));

        adminMeetingRepository.updateMeetingStatus(ON_PROGRESS, FINISHED, List.of(coffeeCoupon, mealCoupon));
        List<Meeting> meetings = adminMeetingRepository.findAllById(
                List.of(coffeeMeeting.getId(), mealMeeting.getId()));

        assertAll(
                () -> assertThat(meetings).hasSize(2),
                () -> assertThat(meetings).extracting("meetingStatus").containsOnly(FINISHED)
        );
    }
}

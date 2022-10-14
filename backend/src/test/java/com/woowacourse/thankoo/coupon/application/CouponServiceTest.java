package com.woowacourse.thankoo.coupon.application;

import static com.woowacourse.thankoo.common.fixtures.CouponFixture.MESSAGE;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.TITLE;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.TYPE;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_IMAGE_URL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_NAME;
import static com.woowacourse.thankoo.common.fixtures.OrganizationFixture.createDefaultOrganization;
import static com.woowacourse.thankoo.coupon.domain.CouponStatus.NOT_USED;
import static com.woowacourse.thankoo.coupon.domain.CouponType.COFFEE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.thankoo.common.annotations.ApplicationTest;
import com.woowacourse.thankoo.coupon.application.dto.ContentCommand;
import com.woowacourse.thankoo.coupon.application.dto.ContentRequest;
import com.woowacourse.thankoo.coupon.application.dto.CouponCommand;
import com.woowacourse.thankoo.coupon.application.dto.CouponRequest;
import com.woowacourse.thankoo.coupon.domain.Coupon;
import com.woowacourse.thankoo.coupon.domain.CouponContent;
import com.woowacourse.thankoo.coupon.domain.CouponRepository;
import com.woowacourse.thankoo.coupon.domain.CouponStatus;
import com.woowacourse.thankoo.coupon.exception.InvalidCouponException;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import com.woowacourse.thankoo.member.exception.InvalidMemberException;
import com.woowacourse.thankoo.organization.application.OrganizationService;
import com.woowacourse.thankoo.organization.application.dto.OrganizationJoinRequest;
import com.woowacourse.thankoo.organization.domain.Organization;
import com.woowacourse.thankoo.organization.domain.OrganizationRepository;
import com.woowacourse.thankoo.organization.domain.OrganizationValidator;
import com.woowacourse.thankoo.organization.exception.InvalidOrganizationException;
import com.woowacourse.thankoo.reservation.exception.InvalidReservationException;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("CouponService 는 ")
@ApplicationTest
@ExtendWith(MockitoExtension.class)
class CouponServiceTest {

    @Autowired
    private CouponService couponService;

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

    @DisplayName("쿠폰을 저장할 때 ")
    @Nested
    class SaveCouponTest {

        @DisplayName("회원이 존재하면 정상적으로 저장한다.")
        @Test
        void save() {
            Member sender = memberRepository.save(new Member(HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, SKRR_IMAGE_URL));
            Member receiver = memberRepository.save(new Member(SKRR_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, SKRR_IMAGE_URL));

            Organization organization = organizationRepository.save(createDefaultOrganization(organizationValidator));
            join(organization.getCode().getValue(), sender.getId(), receiver.getId());

            CouponCommand couponCommand = new CouponCommand(organization.getId(), sender.getId(),
                    List.of(receiver.getId()),
                    new ContentCommand(TYPE, TITLE, MESSAGE));
            couponService.saveAll(couponCommand);

            List<Coupon> coupons = couponRepository.findAll();

            assertThat(coupons).hasSize(1);
        }

        @DisplayName("회원들이 존재하면 정상적으로 저장한다.")
        @Test
        void saveAll() {
            Member sender = memberRepository.save(new Member(HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, SKRR_IMAGE_URL));
            Member receiver1 = memberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, SKRR_IMAGE_URL));
            Member receiver2 = memberRepository.save(new Member(SKRR_NAME, HOHO_EMAIL, HOHO_SOCIAL_ID, SKRR_IMAGE_URL));

            Organization organization = organizationRepository.save(createDefaultOrganization(organizationValidator));
            join(organization.getCode().getValue(), sender.getId(), receiver1.getId(), receiver2.getId());

            CouponCommand couponCommand = new CouponCommand(organization.getId(), sender.getId(),
                    List.of(receiver1.getId(), receiver2.getId()),
                    new ContentCommand(TYPE, TITLE, MESSAGE));
            couponService.saveAll(couponCommand);

            List<Coupon> coupons = couponRepository.findAll();

            assertThat(coupons).hasSize(2);
        }

        @DisplayName("조직에 속한 회원이 아니면 보내지 못한다.")
        @Test
        void saveAllNotOrganizationMember() {
            Member sender = memberRepository.save(new Member(HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, SKRR_IMAGE_URL));
            Member receiver1 = memberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, SKRR_IMAGE_URL));
            Member receiver2 = memberRepository.save(new Member(SKRR_NAME, HOHO_EMAIL, HOHO_SOCIAL_ID, SKRR_IMAGE_URL));

            Organization organization = organizationRepository.save(createDefaultOrganization(organizationValidator));
            join(organization.getCode().getValue(), sender.getId(), receiver1.getId());

            CouponCommand couponCommand = new CouponCommand(organization.getId(), sender.getId(),
                    List.of(receiver1.getId(), receiver2.getId()),
                    new ContentCommand(TYPE, TITLE, MESSAGE));

            assertThatThrownBy(
                    () -> couponService.saveAll(couponCommand))
                    .isInstanceOf(InvalidOrganizationException.class)
                    .hasMessage("조직에 가입되지 않은 회원입니다.");
        }

        @DisplayName("자신에게 보내는 경우 예외가 발생한다.")
        @Test
        void saveInvalidMembersException() {
            Member sender = memberRepository.save(new Member(HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, SKRR_IMAGE_URL));

            Organization organization = organizationRepository.save(createDefaultOrganization(organizationValidator));
            join(organization.getCode().getValue(), sender.getId());

            CouponCommand couponCommand = new CouponCommand(organization.getId(), sender.getId(),
                    List.of(sender.getId()),
                    new ContentCommand(TYPE, TITLE, MESSAGE));

            assertThatThrownBy(
                    () -> couponService.saveAll(couponCommand))
                    .isInstanceOf(InvalidCouponException.class)
                    .hasMessage("쿠폰을 생성할 수 없습니다.");
        }

        @DisplayName("회원이 존재하지 않으면 예외가 발생한다.")
        @Test
        void saveInvalidMemberException() {
            Member sender = memberRepository.save(new Member(HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, SKRR_IMAGE_URL));

            Organization organization = organizationRepository.save(createDefaultOrganization(organizationValidator));
            join(organization.getCode().getValue(), sender.getId());

            CouponCommand couponCommand = new CouponCommand(organization.getId(), sender.getId(),
                    List.of(0L),
                    new ContentCommand(TYPE, TITLE, MESSAGE));

            assertThatThrownBy(
                    () -> couponService.saveAll(couponCommand))
                    .isInstanceOf(InvalidMemberException.class)
                    .hasMessage("존재하지 않는 회원입니다.");
        }
    }

    @DisplayName("쿠폰을 바로 사용할 때")
    @Nested
    class UseImmediatelyTest {

        @DisplayName("즉시 사용할 수 있는 상태라면 쿠폰을 즉시 사용 상태로 변경한다.")
        @Test
        void useCoupon() {
            Member sender = memberRepository.save(new Member(HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, SKRR_IMAGE_URL));
            Member receiver = memberRepository.save(new Member(SKRR_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, SKRR_IMAGE_URL));

            Organization organization = organizationRepository.save(createDefaultOrganization(organizationValidator));
            join(organization.getCode().getValue(), sender.getId(), receiver.getId());

            Coupon coupon = new Coupon(organization.getId(), sender.getId(), receiver.getId(),
                    new CouponContent(COFFEE, TITLE, MESSAGE),
                    NOT_USED);

            Coupon savedCoupon = couponRepository.save(coupon);

            couponService.useImmediately(receiver.getId(), organization.getId(), savedCoupon.getId());

            Coupon usedCoupon = couponRepository.findById(savedCoupon.getId()).orElseThrow();

            assertThat(usedCoupon.getCouponStatus()).isEqualTo(CouponStatus.IMMEDIATELY_USED);
        }

        @DisplayName("조직 내 회원이 아니라면 쿠폰을 즉시 사용할 수 없다.")
        @Test
        void useCouponNotInOrganization() {
            Member sender = memberRepository.save(new Member(HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, SKRR_IMAGE_URL));
            Member receiver = memberRepository.save(new Member(SKRR_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, SKRR_IMAGE_URL));
            Member other = memberRepository.save(new Member(SKRR_NAME, HOHO_EMAIL, HOHO_SOCIAL_ID, SKRR_IMAGE_URL));

            Organization organization = organizationRepository.save(createDefaultOrganization(organizationValidator));
            join(organization.getCode().getValue(), sender.getId(), receiver.getId());

            Coupon coupon = new Coupon(organization.getId(), sender.getId(), receiver.getId(),
                    new CouponContent(COFFEE, TITLE, MESSAGE),
                    NOT_USED);

            Coupon savedCoupon = couponRepository.save(coupon);

            assertThatThrownBy(
                    () -> couponService.useImmediately(other.getId(), organization.getId(), savedCoupon.getId()))
                    .isInstanceOf(InvalidOrganizationException.class)
                    .hasMessage("조직에 가입되지 않은 회원입니다.");
        }

        @DisplayName("받는이가 아니라면 예외가 발생한다.")
        @Test
        void saveInvalidMemberException() {
            Member sender = memberRepository.save(new Member(HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, SKRR_IMAGE_URL));
            Member receiver = memberRepository.save(new Member(SKRR_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, SKRR_IMAGE_URL));
            Member hoho = memberRepository.save(new Member(HOHO_NAME, HOHO_EMAIL, HOHO_SOCIAL_ID, SKRR_IMAGE_URL));

            Organization organization = organizationRepository.save(createDefaultOrganization(organizationValidator));
            join(organization.getCode().getValue(), sender.getId(), receiver.getId(), hoho.getId());

            Coupon coupon = new Coupon(organization.getId(), sender.getId(), receiver.getId(),
                    new CouponContent(COFFEE, TITLE, MESSAGE),
                    NOT_USED);

            Coupon savedCoupon = couponRepository.save(coupon);

            assertThatThrownBy(
                    () -> couponService.useImmediately(hoho.getId(), organization.getId(), savedCoupon.getId()))
                    .isInstanceOf(InvalidMemberException.class)
                    .hasMessage("쿠폰을 즉시 사용할 수 있는 회원이 아닙니다.");
        }

        @DisplayName("유효하지 않은 상태라면 예외가 발생한다.")
        @Test
        void useInvalidCouponStatus() {
            Member sender = memberRepository.save(new Member(HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, SKRR_IMAGE_URL));
            Member receiver = memberRepository.save(new Member(SKRR_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, SKRR_IMAGE_URL));

            Organization organization = organizationRepository.save(createDefaultOrganization(organizationValidator));
            join(organization.getCode().getValue(), sender.getId(), receiver.getId());

            Coupon coupon = new Coupon(organization.getId(), sender.getId(), receiver.getId(),
                    new CouponContent(COFFEE, TITLE, MESSAGE),
                    CouponStatus.RESERVING);

            Coupon savedCoupon = couponRepository.save(coupon);

            assertThatThrownBy(
                    () -> couponService.useImmediately(receiver.getId(), organization.getId(), savedCoupon.getId()))
                    .isInstanceOf(InvalidReservationException.class)
                    .hasMessage("존재하지 않는 예약 상태입니다.");
        }
    }

    private void join(final String code, final Long... memberIds) {
        for (Long memberId : memberIds) {
            organizationService.join(memberId, new OrganizationJoinRequest(code));
        }
    }
}


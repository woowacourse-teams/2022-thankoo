package com.woowacourse.thankoo.admin.coupon.application;

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
import static com.woowacourse.thankoo.coupon.domain.CouponStatus.EXPIRED;
import static com.woowacourse.thankoo.coupon.domain.CouponStatus.NOT_USED;
import static com.woowacourse.thankoo.coupon.domain.CouponStatus.RESERVED;
import static com.woowacourse.thankoo.coupon.domain.CouponStatus.RESERVING;
import static com.woowacourse.thankoo.coupon.domain.CouponType.COFFEE;
import static com.woowacourse.thankoo.coupon.domain.CouponType.MEAL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.thankoo.admin.coupon.application.dto.AdminCouponExpireRequest;
import com.woowacourse.thankoo.admin.coupon.domain.AdminCouponRepository;
import com.woowacourse.thankoo.admin.coupon.exception.AdminInvalidCouponException;
import com.woowacourse.thankoo.admin.member.domain.AdminMemberRepository;
import com.woowacourse.thankoo.admin.organization.domain.AdminOrganizationRepository;
import com.woowacourse.thankoo.common.annotations.ApplicationTest;
import com.woowacourse.thankoo.coupon.domain.Coupon;
import com.woowacourse.thankoo.coupon.domain.CouponContent;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.organization.domain.Organization;
import com.woowacourse.thankoo.organization.domain.OrganizationValidator;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("AdminCouponService 는 ")
@ApplicationTest
class AdminCouponServiceTest {

    @Autowired
    private AdminCouponService adminCouponService;

    @Autowired
    private AdminMemberRepository adminMemberRepository;

    @Autowired
    private AdminCouponRepository adminCouponRepository;

    @Autowired
    private AdminOrganizationRepository adminOrganizationRepository;

    @Autowired
    private OrganizationValidator organizationValidator;

    @DisplayName("쿠폰을 만료시킬 때 ")
    @Nested
    class ExpireCoupon {

        @DisplayName("정상적으로 쿠폰을 만료시킨다.")
        @Test
        void updateCouponStatusExpired() {
            Member sender = adminMemberRepository.save(
                    new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, SKRR_IMAGE_URL));
            Member receiver = adminMemberRepository.save(
                    new Member(HOHO_NAME, HOHO_EMAIL, HOHO_SOCIAL_ID, HUNI_IMAGE_URL));

            Organization organization = adminOrganizationRepository.save(
                    createDefaultOrganization(organizationValidator));
            Coupon notUsedCoupon = adminCouponRepository.save(
                    new Coupon(organization.getId(), sender.getId(), receiver.getId(),
                            new CouponContent(COFFEE, TITLE, MESSAGE), NOT_USED));
            Coupon reservedCoupon = adminCouponRepository.save(
                    new Coupon(organization.getId(), sender.getId(), receiver.getId(),
                            new CouponContent(MEAL, TITLE, MESSAGE), RESERVED));
            Coupon reservingCoupon = adminCouponRepository.save(
                    new Coupon(organization.getId(), sender.getId(), receiver.getId(),
                            new CouponContent(MEAL, TITLE, MESSAGE), RESERVING));

            List<Long> couponIds = List.of(notUsedCoupon.getId(), reservedCoupon.getId(), reservingCoupon.getId());
            adminCouponService.updateCouponStatusExpired(new AdminCouponExpireRequest(couponIds));

            List<Coupon> coupons = adminCouponRepository.findAllById(couponIds);

            assertAll(
                    () -> assertThat(coupons).hasSize(3),
                    () -> assertThat(coupons).extracting("couponStatus").containsOnly(EXPIRED)
            );
        }

        @DisplayName("존재하지 않는 쿠폰일 경우 예외가 발생한다.")
        @Test
        void updateCouponStatusExpiredWithInvalidCoupon() {
            Member sender = adminMemberRepository.save(
                    new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, SKRR_IMAGE_URL));
            Member receiver = adminMemberRepository.save(
                    new Member(HOHO_NAME, HOHO_EMAIL, HOHO_SOCIAL_ID, HUNI_IMAGE_URL));

            Organization organization = adminOrganizationRepository.save(
                    createDefaultOrganization(organizationValidator));
            Coupon notUsedCoupon = adminCouponRepository.save(
                    new Coupon(organization.getId(), sender.getId(), receiver.getId(),
                            new CouponContent(COFFEE, TITLE, MESSAGE), NOT_USED));

            List<Long> couponIds = List.of(notUsedCoupon.getId(), notUsedCoupon.getId() + 1);

            assertThatThrownBy(
                    () -> adminCouponService.updateCouponStatusExpired(new AdminCouponExpireRequest(couponIds)))
                    .isInstanceOf(AdminInvalidCouponException.class)
                    .hasMessage("존재하지 않는 쿠폰입니다.");
        }
    }
}

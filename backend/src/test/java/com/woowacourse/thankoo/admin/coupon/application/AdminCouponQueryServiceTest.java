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
import static com.woowacourse.thankoo.coupon.domain.CouponStatus.NOT_USED;
import static com.woowacourse.thankoo.coupon.domain.CouponStatus.RESERVED;
import static com.woowacourse.thankoo.coupon.domain.CouponType.COFFEE;
import static com.woowacourse.thankoo.coupon.domain.CouponType.MEAL;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.thankoo.admin.coupon.application.dto.AdminCouponSearchRequest;
import com.woowacourse.thankoo.admin.coupon.domain.AdminCouponRepository;
import com.woowacourse.thankoo.admin.coupon.domain.AdminCouponStatus;
import com.woowacourse.thankoo.admin.coupon.presentation.dto.AdminCouponResponse;
import com.woowacourse.thankoo.admin.member.domain.AdminMemberRepository;
import com.woowacourse.thankoo.admin.organization.domain.AdminOrganizationRepository;
import com.woowacourse.thankoo.common.annotations.ApplicationTest;
import com.woowacourse.thankoo.coupon.domain.Coupon;
import com.woowacourse.thankoo.coupon.domain.CouponContent;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.organization.domain.Organization;
import com.woowacourse.thankoo.organization.domain.OrganizationValidator;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("AdminCouponService 는 ")
@ApplicationTest
class AdminCouponQueryServiceTest {

    @Autowired
    private AdminCouponQueryService adminCouponQueryService;

    @Autowired
    private AdminCouponRepository adminCouponRepository;

    @Autowired
    private AdminMemberRepository adminMemberRepository;

    @Autowired
    private AdminOrganizationRepository adminOrganizationRepository;

    @Autowired
    private OrganizationValidator organizationValidator;

    @DisplayName("특정 기간의 모든 쿠폰을 조회한다.")
    @Test
    void getCouponsByDate() {
        Member sender = adminMemberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, SKRR_IMAGE_URL));
        Member receiver = adminMemberRepository.save(new Member(HOHO_NAME, HOHO_EMAIL, HOHO_SOCIAL_ID, HUNI_IMAGE_URL));

        Organization organization = adminOrganizationRepository.save(createDefaultOrganization(organizationValidator));

        adminCouponRepository.save(new Coupon(organization.getId(), sender.getId(), receiver.getId(),
                new CouponContent(COFFEE, TITLE, MESSAGE), NOT_USED));
        adminCouponRepository.save(new Coupon(organization.getId(), sender.getId(), receiver.getId(),
                new CouponContent(MEAL, TITLE, MESSAGE), RESERVED));

        LocalDate startDate = LocalDate.now().minusDays(5L);
        LocalDate endDate = LocalDate.now();
        AdminCouponStatus couponStatus = AdminCouponStatus.ALL;

        List<AdminCouponResponse> coupons = adminCouponQueryService.getCoupons(
                new AdminCouponSearchRequest(startDate, endDate, couponStatus.name()));

        assertThat(coupons).hasSize(2);
    }

    @DisplayName("특정 기간과 상태의 쿠폰을 조회한다.")
    @Test
    void getCouponsByDateAndStatus() {
        Member sender = adminMemberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, SKRR_IMAGE_URL));
        Member receiver = adminMemberRepository.save(new Member(HOHO_NAME, HOHO_EMAIL, HOHO_SOCIAL_ID, HUNI_IMAGE_URL));

        Organization organization = adminOrganizationRepository.save(createDefaultOrganization(organizationValidator));

        adminCouponRepository.save(new Coupon(organization.getId(), sender.getId(), receiver.getId(),
                new CouponContent(COFFEE, TITLE, MESSAGE), NOT_USED));
        adminCouponRepository.save(new Coupon(organization.getId(), sender.getId(), receiver.getId(),
                new CouponContent(MEAL, TITLE, MESSAGE), RESERVED));
        adminCouponRepository.save(new Coupon(organization.getId(), sender.getId(), receiver.getId(),
                new CouponContent(MEAL, TITLE, MESSAGE), RESERVED));

        LocalDate startDate = LocalDate.now().minusDays(5L);
        LocalDate endDate = LocalDate.now();
        AdminCouponStatus couponStatus = AdminCouponStatus.RESERVED;

        List<AdminCouponResponse> coupons = adminCouponQueryService.getCoupons(
                new AdminCouponSearchRequest(startDate, endDate, couponStatus.name()));

        assertThat(coupons).hasSize(2);
    }
}

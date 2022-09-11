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
import static com.woowacourse.thankoo.coupon.domain.CouponStatus.NOT_USED;
import static com.woowacourse.thankoo.coupon.domain.CouponType.COFFEE;
import static com.woowacourse.thankoo.coupon.domain.CouponType.MEAL;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.thankoo.admin.coupon.domain.AdminCouponRepository;
import com.woowacourse.thankoo.admin.coupon.presentation.dto.AdminCouponResponse;
import com.woowacourse.thankoo.admin.member.domain.AdminMemberRepository;
import com.woowacourse.thankoo.common.annotations.ApplicationTest;
import com.woowacourse.thankoo.coupon.domain.Coupon;
import com.woowacourse.thankoo.coupon.domain.CouponContent;
import com.woowacourse.thankoo.member.domain.Member;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("AdminCouponService 는 ")
@ApplicationTest
class AdminCouponServiceTest {

    @Autowired
    private AdminCouponService adminCouponService;

    @Autowired
    private AdminCouponRepository adminCouponRepository;

    @Autowired
    private AdminMemberRepository adminMemberRepository;

    @DisplayName("모든 쿠폰을 조회한다.")
    @Test
    void findAll() {
        Member sender = adminMemberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, SKRR_IMAGE_URL));
        Member receiver = adminMemberRepository.save(new Member(HOHO_NAME, HOHO_EMAIL, HOHO_SOCIAL_ID, HUNI_IMAGE_URL));

        adminCouponRepository.save(new Coupon(sender.getId(), receiver.getId(),
                new CouponContent(COFFEE, TITLE, MESSAGE), NOT_USED));
        adminCouponRepository.save(new Coupon(sender.getId(), receiver.getId(),
                new CouponContent(MEAL, TITLE, MESSAGE), NOT_USED));

        List<AdminCouponResponse> coupons = adminCouponService.getCoupons();

        assertThat(coupons).hasSize(2);
    }
}
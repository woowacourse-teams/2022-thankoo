package com.woowacourse.thankoo.admin.coupon.domain;

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
import static com.woowacourse.thankoo.coupon.domain.CouponStatus.RESERVED;
import static com.woowacourse.thankoo.coupon.domain.CouponType.COFFEE;
import static com.woowacourse.thankoo.coupon.domain.CouponType.MEAL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.thankoo.admin.member.domain.AdminMemberRepository;
import com.woowacourse.thankoo.common.annotations.RepositoryTest;
import com.woowacourse.thankoo.coupon.domain.Coupon;
import com.woowacourse.thankoo.coupon.domain.CouponContent;
import com.woowacourse.thankoo.coupon.domain.MemberCoupon;
import com.woowacourse.thankoo.member.domain.Member;
import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@DisplayName("AdminCouponQueryRepository 는 ")
@RepositoryTest
class AdminCouponQueryRepositoryTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private AdminCouponRepository adminCouponRepository;

    @Autowired
    private AdminMemberRepository adminMemberRepository;

    private AdminCouponQueryRepository adminCouponQueryRepository;

    @BeforeEach
    void setUp() {
        adminCouponQueryRepository = new AdminCouponQueryRepository(new NamedParameterJdbcTemplate(dataSource));
    }

    @DisplayName("모든 쿠폰을 조회한다.")
    @Test
    void findAll() {
        Member sender = adminMemberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, SKRR_IMAGE_URL));
        Member receiver = adminMemberRepository.save(new Member(HOHO_NAME, HOHO_EMAIL, HOHO_SOCIAL_ID, HUNI_IMAGE_URL));

        adminCouponRepository.save(new Coupon(sender.getId(), receiver.getId(),
                new CouponContent(COFFEE, TITLE, MESSAGE), NOT_USED));
        adminCouponRepository.save(new Coupon(sender.getId(), receiver.getId(),
                new CouponContent(MEAL, TITLE, MESSAGE), NOT_USED));

        List<MemberCoupon> coupons = adminCouponQueryRepository.findAll();
        assertThat(coupons).hasSize(2);
    }

    @DisplayName("해당 상태의 쿠폰들을 조회한다.")
    @Test
    void findAllByStatus() {
        Member sender = adminMemberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, SKRR_IMAGE_URL));
        Member receiver = adminMemberRepository.save(new Member(HOHO_NAME, HOHO_EMAIL, HOHO_SOCIAL_ID, HUNI_IMAGE_URL));

        adminCouponRepository.save(new Coupon(sender.getId(), receiver.getId(),
                new CouponContent(COFFEE, TITLE, MESSAGE), NOT_USED));
        adminCouponRepository.save(new Coupon(sender.getId(), receiver.getId(),
                new CouponContent(MEAL, TITLE, MESSAGE), RESERVED));
        adminCouponRepository.save(new Coupon(sender.getId(), receiver.getId(),
                new CouponContent(MEAL, TITLE, MESSAGE), RESERVED));

        String status = RESERVED.name();
        List<MemberCoupon> coupons = adminCouponQueryRepository.findAllByStatus(status);

        assertAll(
                () -> assertThat(coupons).hasSize(2),
                () -> assertThat(coupons).extracting("status").containsOnly(status)
        );
    }
}

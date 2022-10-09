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
import static com.woowacourse.thankoo.common.fixtures.OrganizationFixture.createDefaultOrganization;
import static com.woowacourse.thankoo.coupon.domain.CouponStatus.NOT_USED;
import static com.woowacourse.thankoo.coupon.domain.CouponStatus.RESERVED;
import static com.woowacourse.thankoo.coupon.domain.CouponType.COFFEE;
import static com.woowacourse.thankoo.coupon.domain.CouponType.MEAL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

import com.woowacourse.thankoo.admin.coupon.domain.dto.AdminCouponSearchCondition;
import com.woowacourse.thankoo.admin.member.domain.AdminMemberRepository;
import com.woowacourse.thankoo.admin.organization.domain.AdminOrganizationRepository;
import com.woowacourse.thankoo.common.annotations.RepositoryTest;
import com.woowacourse.thankoo.coupon.domain.Coupon;
import com.woowacourse.thankoo.coupon.domain.CouponContent;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.organization.domain.Organization;
import com.woowacourse.thankoo.organization.domain.OrganizationValidator;
import java.time.LocalDate;
import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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

    @Autowired
    private AdminOrganizationRepository adminOrganizationRepository;

    private OrganizationValidator organizationValidator;

    private AdminCouponQueryRepository adminCouponQueryRepository;

    @BeforeEach
    void setUp() {
        adminCouponQueryRepository = new AdminCouponQueryRepository(new NamedParameterJdbcTemplate(dataSource));
        organizationValidator = Mockito.mock(OrganizationValidator.class);
        doNothing().when(organizationValidator).validate(any(Organization.class));
    }

    @DisplayName("기간에 따른 모든 쿠폰들을 조회한다. (기간 + all)")
    @Test
    void findAllByDateCondition() {
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

        List<AdminCoupon> coupons = adminCouponQueryRepository.findAllByStatusAndDate(
                AdminCouponSearchCondition.of(AdminCouponStatus.ALL, startDate, endDate));

        assertThat(coupons).hasSize(3);
    }

    @DisplayName("상태와 기간에 따른 쿠폰들을 조회힌다. (기간 + 특정 상태)")
    @Test
    void findAllByStatusAndDateCondition() {
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

        List<AdminCoupon> coupons = adminCouponQueryRepository.findAllByStatusAndDate(
                AdminCouponSearchCondition.of(AdminCouponStatus.RESERVED, startDate, endDate));

        assertThat(coupons).hasSize(2);
    }
}

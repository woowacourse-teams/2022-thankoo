package com.woowacourse.thankoo.coupon.domain;

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
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_IMAGE_URL;
import static com.woowacourse.thankoo.common.fixtures.OrganizationFixture.createDefaultOrganization;
import static com.woowacourse.thankoo.common.fixtures.OrganizationFixture.createThankooOrganization;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

import com.woowacourse.thankoo.common.annotations.RepositoryTest;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import com.woowacourse.thankoo.organization.domain.Organization;
import com.woowacourse.thankoo.organization.domain.OrganizationRepository;
import com.woowacourse.thankoo.organization.domain.OrganizationValidator;
import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@DisplayName("CouponQueryRepository 는 ")
@RepositoryTest
class CouponQueryRepositoryTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    private OrganizationValidator organizationValidator;

    private CouponQueryRepository couponQueryRepository;

    @BeforeEach
    void setUp() {
        couponQueryRepository = new CouponQueryRepository(new NamedParameterJdbcTemplate(dataSource));
        organizationValidator = Mockito.mock(OrganizationValidator.class);
        doNothing().when(organizationValidator).validate(any(Organization.class));
    }

    @DisplayName("조직 내 사용하지 않은 받은 coupon 을 조회한다.")
    @Test
    void findByOrganizationIdAndReceiverIdAndStatusNotUsed() {
        Member sender = memberRepository.save(new Member(HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, SKRR_IMAGE_URL));
        Member receiver = memberRepository.save(new Member(HOHO_NAME, HOHO_EMAIL, HOHO_SOCIAL_ID, SKRR_IMAGE_URL));

        Organization organization1 = organizationRepository.save(createDefaultOrganization(organizationValidator));
        Organization organization2 = organizationRepository.save(createThankooOrganization(organizationValidator));

        couponRepository.saveAll(List.of(
                new Coupon(organization1.getId(), sender.getId(), receiver.getId(),
                        new CouponContent(TYPE, TITLE, MESSAGE),
                        CouponStatus.NOT_USED),
                new Coupon(organization2.getId(), sender.getId(), receiver.getId(),
                        new CouponContent(TYPE, TITLE, MESSAGE),
                        CouponStatus.NOT_USED),
                new Coupon(organization1.getId(), sender.getId(), receiver.getId(),
                        new CouponContent(TYPE, TITLE, MESSAGE),
                        CouponStatus.RESERVED),
                new Coupon(organization1.getId(), sender.getId(), receiver.getId(),
                        new CouponContent(TYPE, TITLE, MESSAGE),
                        CouponStatus.USED)
        ));

        List<MemberCoupon> memberCoupons = couponQueryRepository.findByOrganizationIdAndReceiverIdAndStatus(
                organization1.getId(), receiver.getId(), CouponStatusGroup.findStatusNames(NOT_USED));

        assertThat(memberCoupons).hasSize(2);
    }

    @DisplayName("사용한 받은 coupon 을 조회한다.")
    @Test
    void findByReceiverIdAndStatusUsed() {
        Member sender = memberRepository.save(new Member(HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, SKRR_IMAGE_URL));
        Member receiver = memberRepository.save(new Member(HOHO_NAME, HOHO_EMAIL, HOHO_SOCIAL_ID, SKRR_IMAGE_URL));

        Organization organization = organizationRepository.save(createDefaultOrganization(organizationValidator));

        couponRepository.saveAll(List.of(
                new Coupon(organization.getId(), sender.getId(), receiver.getId(),
                        new CouponContent(TYPE, TITLE, MESSAGE),
                        CouponStatus.NOT_USED),
                new Coupon(organization.getId(), sender.getId(), receiver.getId(),
                        new CouponContent(TYPE, TITLE, MESSAGE),
                        CouponStatus.RESERVED),
                new Coupon(organization.getId(), sender.getId(), receiver.getId(),
                        new CouponContent(TYPE, TITLE, MESSAGE),
                        CouponStatus.USED)
        ));

        List<MemberCoupon> memberCoupons = couponQueryRepository.findByOrganizationIdAndReceiverIdAndStatus(
                organization.getId(), receiver.getId(), CouponStatusGroup.findStatusNames(USED));

        assertThat(memberCoupons).hasSize(1);
    }

    @DisplayName("모든 받은 coupon 을 조회한다.")
    @Test
    void findByReceiverIdAndStatusAll() {
        Member sender = memberRepository.save(new Member(HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, SKRR_IMAGE_URL));
        Member receiver = memberRepository.save(new Member(HOHO_NAME, HOHO_EMAIL, HOHO_SOCIAL_ID, SKRR_IMAGE_URL));

        Organization organization = organizationRepository.save(createDefaultOrganization(organizationValidator));

        couponRepository.saveAll(List.of(
                new Coupon(organization.getId(), sender.getId(), receiver.getId(),
                        new CouponContent(TYPE, TITLE, MESSAGE),
                        CouponStatus.NOT_USED),
                new Coupon(organization.getId(), sender.getId(), receiver.getId(),
                        new CouponContent(TYPE, TITLE, MESSAGE),
                        CouponStatus.RESERVED),
                new Coupon(organization.getId(), sender.getId(), receiver.getId(),
                        new CouponContent(TYPE, TITLE, MESSAGE),
                        CouponStatus.USED)
        ));

        List<MemberCoupon> memberCoupons = couponQueryRepository.findByOrganizationIdAndReceiverIdAndStatus(
                organization.getId(), receiver.getId(), CouponStatusGroup.findStatusNames("all"));

        assertThat(memberCoupons).hasSize(3);
    }

    @DisplayName("보낸 coupon 을 조회한다.")
    @Test
    void findBySenderId() {
        Member sender = memberRepository.save(new Member(HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, SKRR_IMAGE_URL));
        Member receiver = memberRepository.save(new Member(HOHO_NAME, HOHO_EMAIL, HOHO_SOCIAL_ID, SKRR_IMAGE_URL));

        Organization organization1 = organizationRepository.save(createDefaultOrganization(organizationValidator));
        Organization organization2 = organizationRepository.save(createThankooOrganization(organizationValidator));

        couponRepository.saveAll(List.of(
                new Coupon(organization1.getId(), sender.getId(), receiver.getId(),
                        new CouponContent(TYPE, TITLE, MESSAGE),
                        CouponStatus.NOT_USED),
                new Coupon(organization2.getId(), sender.getId(), receiver.getId(),
                        new CouponContent(TYPE, TITLE, MESSAGE),
                        CouponStatus.RESERVED),
                new Coupon(organization1.getId(), sender.getId(), receiver.getId(),
                        new CouponContent(TYPE, TITLE, MESSAGE),
                        CouponStatus.USED),
                new Coupon(organization1.getId(), sender.getId(), receiver.getId(),
                        new CouponContent(TYPE, TITLE, MESSAGE),
                        CouponStatus.USED)
        ));

        List<MemberCoupon> memberCoupons = couponQueryRepository.findByOrganizationIdAndSenderId(organization1.getId(),
                sender.getId());

        assertThat(memberCoupons).hasSize(3);
    }

    @DisplayName("쿠폰을 조회한다.")
    @Test
    void findById() {
        Member sender = memberRepository.save(new Member(HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, SKRR_IMAGE_URL));
        Member receiver = memberRepository.save(new Member(HOHO_NAME, HOHO_EMAIL, HOHO_SOCIAL_ID, SKRR_IMAGE_URL));

        Organization organization = organizationRepository.save(createDefaultOrganization(organizationValidator));

        Coupon coupon = couponRepository.save(new Coupon(organization.getId(), sender.getId(), receiver.getId(),
                new CouponContent(TYPE, TITLE, MESSAGE), CouponStatus.NOT_USED));

        MemberCoupon memberCoupon = couponQueryRepository.findByCouponId(coupon.getId()).get();

        assertAll(
                () -> assertThat(memberCoupon).isNotNull(),
                () -> assertThat(memberCoupon.getCouponId()).isEqualTo(coupon.getId())
        );
    }

    @DisplayName("보낸, 받은 쿠폰 개수를 조회한다.")
    @Test
    void getCouponCount() {
        Member sender = memberRepository.save(new Member(HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, SKRR_IMAGE_URL));
        Member receiver = memberRepository.save(new Member(HOHO_NAME, HOHO_EMAIL, HOHO_SOCIAL_ID, SKRR_IMAGE_URL));

        Organization organization = organizationRepository.save(createDefaultOrganization(organizationValidator));

        couponRepository.saveAll(List.of(
                new Coupon(organization.getId(), sender.getId(), receiver.getId(),
                        new CouponContent(TYPE, TITLE, MESSAGE),
                        CouponStatus.NOT_USED),
                new Coupon(organization.getId(), sender.getId(), receiver.getId(),
                        new CouponContent(TYPE, TITLE, MESSAGE),
                        CouponStatus.RESERVED),
                new Coupon(organization.getId(), sender.getId(), receiver.getId(),
                        new CouponContent(TYPE, TITLE, MESSAGE),
                        CouponStatus.USED),
                new Coupon(organization.getId(), receiver.getId(), sender.getId(),
                        new CouponContent(TYPE, TITLE, MESSAGE),
                        CouponStatus.RESERVED),
                new Coupon(organization.getId(), receiver.getId(), sender.getId(),
                        new CouponContent(TYPE, TITLE, MESSAGE),
                        CouponStatus.USED)
        ));

        CouponTotal couponTotal = couponQueryRepository.getCouponCount(sender.getId());
        assertAll(
                () -> assertThat(couponTotal.getSentCount()).isEqualTo(3),
                () -> assertThat(couponTotal.getReceivedCount()).isEqualTo(2)
        );
    }
}

package com.woowacourse.thankoo.coupon.domain;

import static com.woowacourse.thankoo.common.fixtures.CouponFixture.MESSAGE;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.NOT_USED;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.TITLE;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.TYPE;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.IMAGE_URL_SKRR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.thankoo.common.annotations.RepositoryTest;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@DisplayName("CouponQueryRepository 는 ")
@RepositoryTest
public class CouponQueryRepositoryTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private MemberRepository memberRepository;

    private CouponQueryRepository couponQueryRepository;

    @BeforeEach
    void setUp() {
        couponQueryRepository = new CouponQueryRepository(new NamedParameterJdbcTemplate(dataSource));
    }

    @DisplayName("사용하지 않은 받은 coupon 을 조회한다.")
    @Test
    void findByReceiverIdAndStatusNotUsed() {
        Member sender = memberRepository.save(new Member(HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, IMAGE_URL_SKRR));
        Member receiver = memberRepository.save(new Member(HOHO_NAME, HOHO_EMAIL, HOHO_SOCIAL_ID, IMAGE_URL_SKRR));

        couponRepository.save(new Coupon(sender.getId(), receiver.getId(),
                new CouponContent(TYPE, TITLE, MESSAGE), CouponStatus.NOT_USED));
        couponRepository.save(new Coupon(sender.getId(), receiver.getId(),
                new CouponContent(TYPE, TITLE, MESSAGE), CouponStatus.RESERVED));
        couponRepository.save(new Coupon(sender.getId(), receiver.getId(),
                new CouponContent(TYPE, TITLE, MESSAGE), CouponStatus.USED));

        List<MemberCoupon> memberCoupons = couponQueryRepository.findByReceiverIdAndStatus(
                receiver.getId(), CouponStatusGroup.findStatusNames(NOT_USED));

        assertThat(memberCoupons).hasSize(2);
    }

    @DisplayName("사용한 받은 coupon 을 조회한다.")
    @Test
    void findByReceiverIdAndStatusUsed() {
        Member sender = memberRepository.save(new Member(HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, IMAGE_URL_SKRR));
        Member receiver = memberRepository.save(new Member(HOHO_NAME, HOHO_EMAIL, HOHO_SOCIAL_ID, IMAGE_URL_SKRR));

        couponRepository.save(new Coupon(sender.getId(), receiver.getId(),
                new CouponContent(TYPE, TITLE, MESSAGE), CouponStatus.NOT_USED));
        couponRepository.save(new Coupon(sender.getId(), receiver.getId(),
                new CouponContent(TYPE, TITLE, MESSAGE), CouponStatus.EXPIRED));
        couponRepository.save(new Coupon(sender.getId(), receiver.getId(),
                new CouponContent(TYPE, TITLE, MESSAGE), CouponStatus.USED));

        List<MemberCoupon> memberCoupons = couponQueryRepository.findByReceiverIdAndStatus(
                receiver.getId(), CouponStatusGroup.findStatusNames("used"));

        assertThat(memberCoupons).hasSize(2);
    }

    @DisplayName("보낸 coupon 을 조회한다.")
    @Test
    void findBySenderId() {
        Member sender = memberRepository.save(new Member(HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, IMAGE_URL_SKRR));
        Member receiver = memberRepository.save(new Member(HOHO_NAME, HOHO_EMAIL, HOHO_SOCIAL_ID, IMAGE_URL_SKRR));
        couponRepository.save(new Coupon(sender.getId(), receiver.getId(),
                new CouponContent(TYPE, TITLE, MESSAGE), CouponStatus.NOT_USED));
        couponRepository.save(new Coupon(sender.getId(), receiver.getId(),
                new CouponContent(TYPE, TITLE, MESSAGE), CouponStatus.RESERVED));
        couponRepository.save(new Coupon(sender.getId(), receiver.getId(),
                new CouponContent(TYPE, TITLE, MESSAGE), CouponStatus.USED));

        List<MemberCoupon> memberCoupons = couponQueryRepository.findBySenderId(sender.getId());

        assertThat(memberCoupons).hasSize(3);
    }

    @DisplayName("쿠폰을 조회한다.")
    @Test
    void findById() {
        Member sender = memberRepository.save(new Member(HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, IMAGE_URL_SKRR));
        Member receiver = memberRepository.save(new Member(HOHO_NAME, HOHO_EMAIL, HOHO_SOCIAL_ID, IMAGE_URL_SKRR));
        Coupon coupon = couponRepository.save(new Coupon(sender.getId(), receiver.getId(),
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
        Member sender = memberRepository.save(new Member(HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, IMAGE_URL_SKRR));
        Member receiver = memberRepository.save(new Member(HOHO_NAME, HOHO_EMAIL, HOHO_SOCIAL_ID, IMAGE_URL_SKRR));
        couponRepository.save(new Coupon(sender.getId(), receiver.getId(),
                new CouponContent(TYPE, TITLE, MESSAGE), CouponStatus.NOT_USED));
        couponRepository.save(new Coupon(sender.getId(), receiver.getId(),
                new CouponContent(TYPE, TITLE, MESSAGE), CouponStatus.RESERVED));
        couponRepository.save(new Coupon(sender.getId(), receiver.getId(),
                new CouponContent(TYPE, TITLE, MESSAGE), CouponStatus.USED));
        couponRepository.save(new Coupon(receiver.getId(), sender.getId(),
                new CouponContent(TYPE, TITLE, MESSAGE), CouponStatus.RESERVED));
        couponRepository.save(new Coupon(receiver.getId(), sender.getId(),
                new CouponContent(TYPE, TITLE, MESSAGE), CouponStatus.USED));

        CouponTotal couponTotal = couponQueryRepository.getCouponCount(sender.getId());
        assertAll(
                () -> assertThat(couponTotal.getSentCount()).isEqualTo(3),
                () -> assertThat(couponTotal.getReceivedCount()).isEqualTo(2)
        );
    }
}

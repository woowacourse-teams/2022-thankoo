package com.woowacourse.thankoo.coupon.domain;

import static com.woowacourse.thankoo.common.fixtures.CouponFixture.MESSAGE;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.TITLE;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.TYPE;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_IMAGE_URL;
import static com.woowacourse.thankoo.coupon.domain.CouponStatus.NOT_USED;
import static com.woowacourse.thankoo.coupon.domain.CouponStatus.USED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.thankoo.common.annotations.RepositoryTest;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("CouponRepository 는 ")
@RepositoryTest
class CouponRepositoryTest {

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("만남의 상태와 미팅 id로 미팅 상태를 변경한다.")
    @Test
    void updateCouponStatus() {
        Member sender = memberRepository.save(new Member(HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, SKRR_IMAGE_URL));
        Member receiver = memberRepository.save(new Member(HOHO_NAME, HOHO_EMAIL, HOHO_SOCIAL_ID, SKRR_IMAGE_URL));

        List<Long> couponsIds = IntStream.range(0, 3)
                .mapToObj(coupon -> couponRepository.save(givenCoupon(sender, receiver)))
                .map(Coupon::getId)
                .collect(Collectors.toList());

        couponRepository.updateCouponStatus(USED, couponsIds);

        List<Coupon> coupons = couponRepository.findAll();

        assertAll(
                () -> assertThat(coupons).hasSize(3),
                () -> assertThat(coupons).extracting("couponStatus").containsOnly(USED)
        );
    }

    private Coupon givenCoupon(final Member sender, final Member receiver) {
        return new Coupon(sender.getId(), receiver.getId(), new CouponContent(TYPE, TITLE, MESSAGE), NOT_USED);
    }
}

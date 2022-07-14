package com.woowacourse.thankoo.coupon.application;

import static com.woowacourse.thankoo.common.fixtures.CouponFixture.MESSAGE;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.NOT_USED;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.TITLE;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.TYPE;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.IMAGE_URL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_SOCIAL_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.thankoo.coupon.application.dto.ContentRequest;
import com.woowacourse.thankoo.coupon.application.dto.CouponRequest;
import com.woowacourse.thankoo.coupon.domain.Coupon;
import com.woowacourse.thankoo.coupon.domain.CouponRepository;
import com.woowacourse.thankoo.coupon.presentation.dto.CouponResponse;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import com.woowacourse.thankoo.member.exception.InvalidMemberException;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@DisplayName("CouponService 는 ")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("쿠폰을 저장할 때 ")
    @Nested
    class SaveCouponTest {

        @DisplayName("회원이 존재하면 정상적으로 저장한다.")
        @Test
        void save() {
            Member sender = memberRepository.save(new Member(HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, IMAGE_URL));
            Member receiver = memberRepository.save(new Member(SKRR_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, IMAGE_URL));
            couponService.saveAll(sender.getId(), new CouponRequest(List.of(receiver.getId()),
                    new ContentRequest(TYPE, TITLE, MESSAGE)));

            List<Coupon> coupons = couponRepository.findAll();

            assertThat(coupons).hasSize(1);
        }

        @DisplayName("회원이 존재하면 정상적으로 저장한다.")
        @Test
        void saveAll() {
            Member sender = memberRepository.save(new Member(HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, IMAGE_URL));
            Member receiver1 = memberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, IMAGE_URL));
            Member receiver2 = memberRepository.save(new Member(SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, IMAGE_URL));

            couponService.saveAll(sender.getId(), new CouponRequest(List.of(receiver1.getId(), receiver2.getId()),
                    new ContentRequest(TYPE, TITLE, MESSAGE)));

            List<Coupon> coupons = couponRepository.findAll();

            assertThat(coupons).hasSize(2);
        }

        @DisplayName("회원이 존재하지 않으면 예외가 발생한다.")
        @Test
        void saveInvalidMemberException() {
            Member sender = memberRepository.save(new Member(HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, IMAGE_URL));

            assertThatThrownBy(() -> couponService.saveAll(sender.getId(), new CouponRequest(List.of(0L),
                    new ContentRequest(TYPE, TITLE, MESSAGE))))
                    .isInstanceOf(InvalidMemberException.class)
                    .hasMessage("존재하지 않는 회원입니다.");
        }
    }

    @DisplayName("받은 쿠폰을 조회한다.")
    @Test
    void getReceivedCoupons() {
        Member sender = memberRepository.save(new Member(HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, IMAGE_URL));
        Member receiver = memberRepository.save(new Member(SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, IMAGE_URL));

        couponService.saveAll(sender.getId(), new CouponRequest(List.of(receiver.getId()),
                new ContentRequest(TYPE, TITLE, MESSAGE)));

        List<CouponResponse> responses = couponService.getReceivedCoupons(receiver.getId(), NOT_USED);

        assertAll(
                () -> assertThat(responses).hasSize(1),
                () -> assertThat(responses.get(0).getSender().getId()).isEqualTo(sender.getId()),
                () -> assertThat(responses.get(0).getReceiver().getId()).isEqualTo(receiver.getId())
        );
    }

    @AfterEach
    void tearDown() {
        couponRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();
    }
}

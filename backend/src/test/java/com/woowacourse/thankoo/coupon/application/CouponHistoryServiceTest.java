package com.woowacourse.thankoo.coupon.application;

import static com.woowacourse.thankoo.common.fixtures.CouponFixture.MESSAGE;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.TITLE;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.TYPE;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_NAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.thankoo.coupon.application.dto.ContentRequest;
import com.woowacourse.thankoo.coupon.application.dto.CouponRequest;
import com.woowacourse.thankoo.coupon.domain.CouponHistoryRepository;
import com.woowacourse.thankoo.coupon.presentation.dto.CouponHistoryResponse;
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

@DisplayName("CouponHistoryService 는 ")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class CouponHistoryServiceTest {

    @Autowired
    private CouponHistoryService couponHistoryService;

    @Autowired
    private CouponHistoryRepository couponHistoryRepository;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("쿠폰을 저장할 때 ")
    @Nested
    class SaveCouponTest {

        @DisplayName("회원이 존재하면 정상적으로 저장한다.")
        @Test
        void save() {
            Member sender = memberRepository.save(HUNI);
            Member receiver = memberRepository.save(SKRR);

            List<Long> ids = couponHistoryService.saveAll(sender.getId(), new CouponRequest(List.of(receiver.getId()),
                    new ContentRequest(TYPE, TITLE, MESSAGE)));

            assertThat(ids).hasSize(1);
        }

        @DisplayName("회원이 존재하면 정상적으로 저장한다.")
        @Test
        void saveAll() {
            Member sender = memberRepository.save(new Member(HUNI_NAME));
            Member receiver1 = memberRepository.save(new Member(LALA_NAME));
            Member receiver2 = memberRepository.save(new Member(SKRR_NAME));

            List<Long> ids = couponHistoryService.saveAll(sender.getId(),
                    new CouponRequest(List.of(receiver1.getId(), receiver2.getId()),
                            new ContentRequest(TYPE, TITLE, MESSAGE)));

            assertThat(ids).hasSize(2);
        }

        @DisplayName("회원이 존재하지 않으면 예외가 발생한다.")
        @Test
        void saveInvalidMemberException() {
            Member sender = memberRepository.save(HUNI);

            assertThatThrownBy(() -> couponHistoryService.saveAll(sender.getId(), new CouponRequest(List.of(0L),
                    new ContentRequest(TYPE, TITLE, MESSAGE))))
                    .isInstanceOf(InvalidMemberException.class)
                    .hasMessage("존재하지 않는 회원입니다.");
        }
    }

    @DisplayName("받은 쿠폰을 조회한다.")
    @Test
    void getReceivedCoupons() {
        Member sender = memberRepository.save(HUNI);
        Member receiver = memberRepository.save(SKRR);

        couponHistoryService.saveAll(sender.getId(), new CouponRequest(List.of(receiver.getId()),
                new ContentRequest(TYPE, TITLE, MESSAGE)));

        List<CouponHistoryResponse> responses = couponHistoryService.getReceivedCoupons(receiver.getId());

        assertAll(
                () -> assertThat(responses).hasSize(1),
                () -> assertThat(responses.get(0).getSender().getId()).isEqualTo(sender.getId()),
                () -> assertThat(responses.get(0).getReceiver().getId()).isEqualTo(receiver.getId())
        );
    }

    @AfterEach
    void tearDown() {
        couponHistoryRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();
    }
}

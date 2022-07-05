package com.woowacourse.thankoo.coupon.application;

import static com.woowacourse.thankoo.common.fixtures.TestFixture.MESSAGE;
import static com.woowacourse.thankoo.common.fixtures.TestFixture.TITLE;
import static com.woowacourse.thankoo.common.fixtures.TestFixture.TYPE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.thankoo.coupon.application.dto.ContentRequest;
import com.woowacourse.thankoo.coupon.application.dto.CouponRequest;
import com.woowacourse.thankoo.coupon.domain.CouponHistoryRepository;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import com.woowacourse.thankoo.member.exception.InvalidMemberException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@DisplayName("CouponHistoryService 는")
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
            Member sender = memberRepository.save(new Member("huni"));
            Member receiver = memberRepository.save(new Member("skrrrr"));

            Long id = couponHistoryService.save(sender.getId(), new CouponRequest(receiver.getId(),
                    new ContentRequest(TYPE, TITLE, MESSAGE)));

            assertThat(id).isNotNull();
        }

        @DisplayName("회원이 존재하지 않으면 예외가 발생한다.")
        @Test
        void saveInvalidMemberException() {
            Member sender = memberRepository.save(new Member("huni"));

            assertThatThrownBy(() -> couponHistoryService.save(sender.getId(), new CouponRequest(0L,
                    new ContentRequest(TYPE, TITLE, MESSAGE))))
                    .isInstanceOf(InvalidMemberException.class)
                    .hasMessage("존재하지 않는 회원입니다.");
        }
    }

    @AfterEach
    void tearDown() {
        couponHistoryRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();
    }
}

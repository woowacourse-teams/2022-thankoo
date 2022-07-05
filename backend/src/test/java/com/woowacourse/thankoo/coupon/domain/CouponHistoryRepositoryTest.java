package com.woowacourse.thankoo.coupon.domain;

import static com.woowacourse.thankoo.common.fixtures.TestFixture.MESSAGE;
import static com.woowacourse.thankoo.common.fixtures.TestFixture.TITLE;
import static com.woowacourse.thankoo.common.fixtures.TestFixture.TYPE;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DisplayName("CouponHistoryRepository 는 ")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class CouponHistoryRepositoryTest {

    @Autowired
    private CouponHistoryRepository couponHistoryRepository;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("받은 쿠폰을 조회한다.")
    @Test
    void findByReceiverId() {
        Member sender = memberRepository.save(new Member("huni"));
        Member receiver = memberRepository.save(new Member("hoho"));

        couponHistoryRepository.save(new CouponHistory(sender.getId(), receiver.getId(), TYPE, TITLE, MESSAGE));
        List<CouponHistory> couponHistories = couponHistoryRepository.findByReceiverId(receiver.getId());

        assertThat(couponHistories).hasSize(1);
    }
}

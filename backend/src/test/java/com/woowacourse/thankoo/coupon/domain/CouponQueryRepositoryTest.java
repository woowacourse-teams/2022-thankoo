package com.woowacourse.thankoo.coupon.domain;

import static com.woowacourse.thankoo.common.fixtures.CouponFixture.MESSAGE;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.TITLE;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.TYPE;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@DisplayName("CouponQueryRepository 는 ")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
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

    @DisplayName("받은 coupon 을 조회한다.")
    @Test
    void findByReceiverId() {
        Member sender = memberRepository.save(HUNI);
        Member receiver = memberRepository.save(HOHO);

        couponRepository.save(new Coupon(sender.getId(), receiver.getId(),
                new CouponContent(TYPE, TITLE, MESSAGE), CouponStatus.NOT_USED));

        List<MemberCoupon> memberCoupons = couponQueryRepository.findByReceiverIdAndStatus(
                receiver.getId(), CouponStatusGroup.findStatusNames("not_used"));

        assertThat(memberCoupons).hasSize(1);
    }
}

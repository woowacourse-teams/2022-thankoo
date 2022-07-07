package com.woowacourse.thankoo.coupon.domain;

import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI;
import static com.woowacourse.thankoo.common.fixtures.TestFixture.MESSAGE;
import static com.woowacourse.thankoo.common.fixtures.TestFixture.TITLE;
import static com.woowacourse.thankoo.common.fixtures.TestFixture.TYPE;
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

@DisplayName("CouponHistoryQueryRepository 는 ")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class CouponHistoryQueryRepositoryTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private CouponHistoryRepository couponHistoryRepository;

    @Autowired
    private MemberRepository memberRepository;

    private CouponHistoryQueryRepository couponHistoryQueryRepository;

    @BeforeEach
    void setUp() {
        couponHistoryQueryRepository = new CouponHistoryQueryRepository(new NamedParameterJdbcTemplate(dataSource));
    }

    @DisplayName("받은 coupon history 를 조회한다.")
    @Test
    void findByReceiverId() {
        Member sender = memberRepository.save(HUNI);
        Member receiver = memberRepository.save(HOHO);

        couponHistoryRepository.save(new CouponHistory(sender.getId(), receiver.getId(), TYPE, TITLE, MESSAGE));

        List<MemberCouponHistory> memberCouponHistories = couponHistoryQueryRepository.findByReceiverId(
                receiver.getId());

        assertThat(memberCouponHistories).hasSize(1);
    }
}

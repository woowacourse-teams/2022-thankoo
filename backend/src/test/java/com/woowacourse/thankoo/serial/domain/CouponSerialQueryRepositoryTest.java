package com.woowacourse.thankoo.serial.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.thankoo.common.annotations.RepositoryTest;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@DisplayName("CouponSerialQueryRepository 는 ")
@RepositoryTest
class CouponSerialQueryRepositoryTest {

    private CouponSerialQueryRepository couponSerialQueryRepository;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private CouponSerialRepository couponSerialRepository;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        couponSerialQueryRepository = new CouponSerialQueryRepository(new NamedParameterJdbcTemplate(dataSource));
    }

    @DisplayName("시리얼 코드로 쿠폰 시리얼을 조회한다.")
    @Test
    void findByCode() {
        Member member = memberRepository.save(new Member("네오", "neo@email.com", "네네", "image.png"));
        couponSerialRepository.save(new CouponSerial("1234", member.getId(), CouponType.COFFEE));

        CouponSerialMember couponSerialMember = couponSerialQueryRepository.findByCode("1234").get();

        assertThat(couponSerialMember.getMemberName()).isEqualTo("네오");
    }
}

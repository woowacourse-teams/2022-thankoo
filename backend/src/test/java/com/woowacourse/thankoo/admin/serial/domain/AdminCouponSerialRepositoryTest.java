package com.woowacourse.thankoo.admin.serial.domain;

import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_IMAGE_URL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.NEO_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.NEO_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.NEO_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.SerialFixture.SERIAL_1;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.thankoo.common.annotations.RepositoryTest;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import com.woowacourse.thankoo.serial.domain.CouponSerial;
import com.woowacourse.thankoo.serial.domain.CouponSerialRepository;
import com.woowacourse.thankoo.serial.domain.CouponSerialType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("AdminCouponSerialRepository 는 ")
@RepositoryTest
class AdminCouponSerialRepositoryTest {

    @Autowired
    private CouponSerialRepository couponSerialRepository;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("코드가 존재하는지 확인한다.")
    @Test
    void existsByCode() {
        Member sender = memberRepository.save(new Member(NEO_NAME, NEO_EMAIL, NEO_SOCIAL_ID, HUNI_IMAGE_URL));

        couponSerialRepository.save(new CouponSerial(SERIAL_1, sender.getId(), CouponSerialType.COFFEE));

        assertThat(couponSerialRepository.existsByCode(SERIAL_1)).isTrue();
    }
}

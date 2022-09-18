package com.woowacourse.thankoo.admin.serial.application;

import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_IMAGE_URL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_IMAGE_URL;
import static com.woowacourse.thankoo.common.fixtures.SerialFixture.SERIAL_1;
import static com.woowacourse.thankoo.common.fixtures.SerialFixture.SERIAL_2;
import static com.woowacourse.thankoo.common.fixtures.SerialFixture.SERIAL_3;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.woowacourse.thankoo.admin.serial.presentation.dto.CouponSerialResponse;
import com.woowacourse.thankoo.common.annotations.ApplicationTest;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import com.woowacourse.thankoo.serial.domain.CouponSerial;
import com.woowacourse.thankoo.serial.domain.CouponSerialRepository;
import com.woowacourse.thankoo.serial.domain.CouponSerialType;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.test.context.SpringBootTest;

@DisplayName("AdminCouponSerialQueryService 는 ")
@ApplicationTest
class AdminCouponSerialQueryServiceTest {

    @Autowired
    private AdminCouponSerialQueryService adminCouponSerialQueryService;

    @Autowired
    private CouponSerialRepository couponSerialRepository;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("회원의 id로 쿠폰 시리얼을 모두 조회한다.")
    @Test
    void getByMemberId() {
        Member member = memberRepository.save(new Member("네오", "neo@woowa.com", "네오네오", HUNI_IMAGE_URL));
        Member hoho = memberRepository.save(new Member(HOHO_NAME, HOHO_EMAIL, HOHO_SOCIAL_ID, SKRR_IMAGE_URL));

        couponSerialRepository.save(new CouponSerial(SERIAL_1, member.getId(), CouponSerialType.COFFEE));
        couponSerialRepository.save(new CouponSerial(SERIAL_2, member.getId(), CouponSerialType.COFFEE));
        couponSerialRepository.save(new CouponSerial(SERIAL_3, hoho.getId(), CouponSerialType.COFFEE));

        List<CouponSerialResponse> couponSerialResponses = adminCouponSerialQueryService.getByMemberId(member.getId());

        assertThat(couponSerialResponses).hasSize(2);
    }
}

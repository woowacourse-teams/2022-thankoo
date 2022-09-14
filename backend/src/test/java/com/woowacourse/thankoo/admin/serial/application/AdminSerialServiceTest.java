package com.woowacourse.thankoo.admin.serial.application;

import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_IMAGE_URL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.thankoo.admin.serial.application.dto.SerialRequest;
import com.woowacourse.thankoo.admin.serial.domain.Serial;
import com.woowacourse.thankoo.admin.serial.domain.SerialRepository;
import com.woowacourse.thankoo.common.annotations.ApplicationTest;
import com.woowacourse.thankoo.coupon.domain.CouponType;
import com.woowacourse.thankoo.coupon.exception.InvalidCouponContentException;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import com.woowacourse.thankoo.member.exception.InvalidMemberException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@ApplicationTest
class AdminSerialServiceTest {

    @Autowired
    private AdminSerialService serialService;

    @Autowired
    private SerialRepository serialRepository;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("쿠폰 시리얼을 생성할 때 ")
    @Nested
    class CreateSerial {

        @DisplayName("쿠폰 시리얼을 생성한다.")
        @Test
        void save() {
            memberRepository.save(new Member("네오", "neo@woowa.com", "네오네오", HUNI_IMAGE_URL));

            Long serialId = serialService.save(new SerialRequest("네오", "COFFEE", "1234"));
            Serial serial = serialRepository.findById(serialId).get();

            assertAll(
                    () -> assertThat(serial.getCode()).isEqualTo("1234"),
                    () -> assertThat(serial.getCouponType()).isEqualTo(CouponType.COFFEE)
            );
        }

        @DisplayName("코치를 찾지 못 할 경우 예외를 발생한다.")
        @Test
        void notFoundCoach() {
            memberRepository.save(new Member("네오", "neo@woowa.com", "네오네오", HUNI_IMAGE_URL));

            assertThatThrownBy(() -> serialService.save(new SerialRequest("제이슨", "COFFEE", "1234")))
                    .isInstanceOf(InvalidMemberException.class)
                    .hasMessage("존재하지 않는 회원입니다.");
        }

        @DisplayName("존재하지 않는 쿠폰 타입일 경우 예외를 발생한다.")
        @Test
        void notFoundCouponType() {
            memberRepository.save(new Member("네오", "neo@woowa.com", "네오네오", HUNI_IMAGE_URL));

            assertThatThrownBy(() -> serialService.save(new SerialRequest("네오", "NOOP", "1234")))
                    .isInstanceOf(InvalidCouponContentException.class)
                    .hasMessage("존재하지 않는 쿠폰 타입입니다.");
        }
    }
}

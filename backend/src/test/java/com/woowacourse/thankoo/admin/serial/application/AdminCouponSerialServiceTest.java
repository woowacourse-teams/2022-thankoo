package com.woowacourse.thankoo.admin.serial.application;

import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_IMAGE_URL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.NEO_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.NEO_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.NEO_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.SerialFixture.SERIAL_1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.thankoo.common.annotations.ApplicationTest;
import com.woowacourse.thankoo.coupon.exception.InvalidCouponContentException;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import com.woowacourse.thankoo.member.exception.InvalidMemberException;
import com.woowacourse.thankoo.serial.application.dto.CouponSerialRequest;
import com.woowacourse.thankoo.serial.domain.CouponSerial;
import com.woowacourse.thankoo.serial.domain.CouponSerialRepository;
import com.woowacourse.thankoo.serial.domain.CouponSerialType;
import com.woowacourse.thankoo.serial.exeption.InvalidCouponSerialException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("CouponCouponSerialService 는 ")
@ApplicationTest
class AdminCouponSerialServiceTest {

    @Autowired
    private AdminCouponSerialService adminCouponSerialService;

    @Autowired
    private CouponSerialRepository couponSerialRepository;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("쿠폰 시리얼을 생성할 때 ")
    @Nested
    class CreateCouponSerial {

        @DisplayName("쿠폰 시리얼을 생성한다.")
        @Test
        void save() {
            Member member = memberRepository.save(new Member(NEO_NAME, NEO_EMAIL, NEO_SOCIAL_ID, HUNI_IMAGE_URL));

            Long serialId = adminCouponSerialService.save(new CouponSerialRequest(member.getId(), "COFFEE", SERIAL_1));
            CouponSerial couponSerial = couponSerialRepository.findById(serialId).get();

            assertAll(
                    () -> assertThat(couponSerial.getCode()).isEqualTo(SERIAL_1),
                    () -> assertThat(couponSerial.getCouponSerialType()).isEqualTo(CouponSerialType.COFFEE)
            );
        }

        @DisplayName("코치를 찾지 못 할 경우 예외를 발생한다.")
        @Test
        void notFoundCoach() {
            Member member = memberRepository.save(new Member(NEO_NAME, NEO_EMAIL, NEO_SOCIAL_ID, HUNI_IMAGE_URL));

            assertThatThrownBy(() -> adminCouponSerialService.save(
                    new CouponSerialRequest(member.getId() + 1, "COFFEE", SERIAL_1)))
                    .isInstanceOf(InvalidMemberException.class)
                    .hasMessage("존재하지 않는 회원입니다.");
        }

        @DisplayName("존재하지 않는 쿠폰 타입일 경우 예외를 발생한다.")
        @Test
        void notFoundCouponType() {
            Member member = memberRepository.save(new Member(NEO_NAME, NEO_EMAIL, NEO_SOCIAL_ID, HUNI_IMAGE_URL));

            assertThatThrownBy(
                    () -> adminCouponSerialService.save(new CouponSerialRequest(member.getId(), "NOOP", SERIAL_1)))
                    .isInstanceOf(InvalidCouponContentException.class)
                    .hasMessage("존재하지 않는 쿠폰 타입입니다.");
        }

        @DisplayName("시리얼 번호가 중복되는 경우 예외를 발생한다.")
        @Test
        void duplicateSerial() {
            Member member = memberRepository.save(new Member(NEO_NAME, NEO_EMAIL, NEO_SOCIAL_ID, HUNI_IMAGE_URL));
            adminCouponSerialService.save(new CouponSerialRequest(member.getId(), "COFFEE", SERIAL_1));

            assertThatThrownBy(
                    () -> adminCouponSerialService.save(new CouponSerialRequest(member.getId(), "COFFEE", SERIAL_1)))
                    .isInstanceOf(InvalidCouponSerialException.class)
                    .hasMessage("시리얼 번호가 중복됩니다.");
        }
    }
}

package com.woowacourse.thankoo.serial.application;

import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_IMAGE_URL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.NEO_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.NEO_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.NEO_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.SerialFixture.NEO_MESSAGE;
import static com.woowacourse.thankoo.common.fixtures.SerialFixture.NEO_TITLE;
import static com.woowacourse.thankoo.common.fixtures.SerialFixture.SERIAL_1;
import static com.woowacourse.thankoo.serial.domain.CouponSerialStatus.NOT_USED;
import static com.woowacourse.thankoo.serial.domain.CouponSerialType.COFFEE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.thankoo.common.annotations.ApplicationTest;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import com.woowacourse.thankoo.member.exception.InvalidMemberException;
import com.woowacourse.thankoo.serial.domain.CouponSerial;
import com.woowacourse.thankoo.serial.domain.CouponSerialRepository;
import com.woowacourse.thankoo.serial.exeption.InvalidCouponSerialException;
import com.woowacourse.thankoo.serial.presentation.dto.CouponSerialResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("CouponSerialQueryService 는 ")
@ApplicationTest
class CouponSerialQueryServiceTest {

    @Autowired
    private CouponSerialQueryService couponSerialQueryService;

    @Autowired
    private CouponSerialRepository couponSerialRepository;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("시리얼 번호로 쿠폰 시리얼을 조회할 때 ")
    @Nested
    class GetCouponSerial {

        @DisplayName("존재하는 경우 쿠폰 시리얼을 반환한다.")
        @Test
        void getCouponSerialByCode() {
            Member member = memberRepository.save(new Member(NEO_NAME, NEO_EMAIL, NEO_SOCIAL_ID, HUNI_IMAGE_URL));

            couponSerialRepository.save(
                    new CouponSerial(SERIAL_1, member.getId(), COFFEE, NOT_USED, NEO_TITLE, NEO_MESSAGE));

            CouponSerialResponse response = couponSerialQueryService.getByCode(member.getId(), SERIAL_1);

            assertAll(
                    () -> assertThat(response.getCouponType()).isEqualTo("COFFEE"),
                    () -> assertThat(response.getSenderName()).isEqualTo(NEO_NAME),
                    () -> assertThat(response.getSenderId()).isEqualTo(member.getId())
            );
        }

        @DisplayName("시리얼 코드가 존재하지 않는 경우 예외를 발생한다.")
        @Test
        void getCouponSerialByNotExistsSerialCode() {
            Member member = memberRepository.save(new Member(NEO_NAME, NEO_EMAIL, NEO_SOCIAL_ID, HUNI_IMAGE_URL));

            assertThatThrownBy(() -> couponSerialQueryService.getByCode(member.getId(), SERIAL_1))
                    .isInstanceOf(InvalidCouponSerialException.class)
                    .hasMessage("존재하지 않는 쿠폰 시리얼 번호입니다.");
        }

        @DisplayName("회원이 존재하지 않는 경우 예외를 발생한다.")
        @Test
        void getCouponSerialByNotExistsMember() {
            couponSerialRepository.save(new CouponSerial(SERIAL_1, 1L, COFFEE, NOT_USED, NEO_TITLE, NEO_MESSAGE));

            assertThatThrownBy(() -> couponSerialQueryService.getByCode(1L, SERIAL_1))
                    .isInstanceOf(InvalidMemberException.class)
                    .hasMessage("존재하지 않는 회원입니다.");
        }
    }
}

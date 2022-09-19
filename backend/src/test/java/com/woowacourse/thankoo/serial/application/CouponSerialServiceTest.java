package com.woowacourse.thankoo.serial.application;

import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_IMAGE_URL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.NEO_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.NEO_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.NEO_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_IMAGE_URL;
import static com.woowacourse.thankoo.common.fixtures.SerialFixture.SERIAL_1;
import static com.woowacourse.thankoo.serial.domain.CouponSerialStatus.NOT_USED;
import static com.woowacourse.thankoo.serial.domain.CouponSerialType.COFFEE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.thankoo.common.annotations.ApplicationTest;
import com.woowacourse.thankoo.coupon.domain.Coupon;
import com.woowacourse.thankoo.coupon.domain.CouponStatus;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import com.woowacourse.thankoo.member.exception.InvalidMemberException;
import com.woowacourse.thankoo.serial.domain.CouponSerial;
import com.woowacourse.thankoo.serial.domain.CouponSerialRepository;
import com.woowacourse.thankoo.serial.exeption.InvalidCouponSerialException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("CouponSerialService 는 ")
@ApplicationTest
class CouponSerialServiceTest {

    @Autowired
    private CouponSerialService couponSerialService;

    @Autowired
    private CouponSerialRepository couponSerialRepository;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("받는이와 시리얼 번호로 쿠폰을 생성할 때")
    @Nested
    class CreateCouponSerial {

        @DisplayName("모두 존재하는 경우 쿠폰을 생성한다.")
        @Test
        void create() {
            Member sender = memberRepository.save(new Member(NEO_NAME, NEO_EMAIL, NEO_SOCIAL_ID, HUNI_IMAGE_URL));
            Member receiver = memberRepository.save(new Member(HOHO_NAME, HOHO_EMAIL, HOHO_SOCIAL_ID, SKRR_IMAGE_URL));

            couponSerialRepository.save(new CouponSerial(SERIAL_1, sender.getId(), COFFEE, NOT_USED));

            Coupon coupon = couponSerialService.create(receiver.getId(), SERIAL_1);

            assertThat(coupon.getSenderId()).isEqualTo(sender.getId());
            assertThat(coupon.getReceiverId()).isEqualTo(receiver.getId());
            assertThat(coupon.getCouponStatus()).isEqualTo(CouponStatus.NOT_USED);
        }

        @DisplayName("받는 이가 존재하지 않는 경우 예외를 발생한다.")
        @Test
        void notFoundMember() {
            Member sender = memberRepository.save(new Member(NEO_NAME, NEO_EMAIL, NEO_SOCIAL_ID, HUNI_IMAGE_URL));

            couponSerialRepository.save(new CouponSerial(SERIAL_1, sender.getId(), COFFEE, NOT_USED));

            assertThatThrownBy(() -> couponSerialService.create(sender.getId() + 1L, SERIAL_1))
                    .isInstanceOf(InvalidMemberException.class)
                    .hasMessage("존재하지 않는 회원입니다.");
        }

        @DisplayName("시리얼 번호가 존재하지 않는 경우 예외를 발생한다.")
        @Test
        void notFoundSerial() {
            Member receiver = memberRepository.save(new Member(HOHO_NAME, HOHO_EMAIL, HOHO_SOCIAL_ID, SKRR_IMAGE_URL));
            Member sender = memberRepository.save(new Member(NEO_NAME, NEO_EMAIL, NEO_SOCIAL_ID, HUNI_IMAGE_URL));

            assertThatThrownBy(() -> couponSerialService.create(receiver.getId(), "1234"))
                    .isInstanceOf(InvalidCouponSerialException.class)
                    .hasMessage("존재하지 않는 쿠폰 시리얼 번호입니다.");
        }
    }
}

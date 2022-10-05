package com.woowacourse.thankoo.serial.application;

import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_IMAGE_URL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.NEO_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.NEO_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.NEO_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_IMAGE_URL;
import static com.woowacourse.thankoo.common.fixtures.SerialFixture.NEO_MESSAGE;
import static com.woowacourse.thankoo.common.fixtures.SerialFixture.NEO_TITLE;
import static com.woowacourse.thankoo.common.fixtures.SerialFixture.SERIAL_1;
import static com.woowacourse.thankoo.serial.domain.CouponSerialStatus.NOT_USED;
import static com.woowacourse.thankoo.serial.domain.CouponSerialStatus.USED;
import static com.woowacourse.thankoo.serial.domain.CouponSerialType.COFFEE;
import static com.woowacourse.thankoo.serial.domain.CouponSerialType.MEAL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.thankoo.common.annotations.ApplicationTest;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import com.woowacourse.thankoo.member.exception.InvalidMemberException;
import com.woowacourse.thankoo.serial.application.dto.CouponSerialRequest;
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

        @DisplayName("유효한 경우 쿠폰을 생성하고 만료처리한다. (커피)")
        @Test
        void useCoffeeCouponSerial() {
            Member sender = memberRepository.save(new Member(NEO_NAME, NEO_EMAIL, NEO_SOCIAL_ID, HUNI_IMAGE_URL));
            Member receiver = memberRepository.save(new Member(HOHO_NAME, HOHO_EMAIL, HOHO_SOCIAL_ID, SKRR_IMAGE_URL));

            CouponSerial notUsedSerial = couponSerialRepository.save(
                    new CouponSerial(SERIAL_1, sender.getId(), COFFEE, NOT_USED, NEO_TITLE, NEO_MESSAGE));

            couponSerialService.use(receiver.getId(), new CouponSerialRequest(SERIAL_1));

            CouponSerial usedSerial = couponSerialRepository.findById(notUsedSerial.getId()).get();

            assertThat(usedSerial.getStatus()).isEqualTo(USED);
            assertThat(usedSerial.getCouponSerialType()).isEqualTo(COFFEE);
            assertThat(usedSerial.getContent().getTitle()).isEqualTo(NEO_TITLE);
            assertThat(usedSerial.getContent().getMessage()).isEqualTo(NEO_MESSAGE);
        }

        @DisplayName("유효한 경우 쿠폰을 생성하고 만료처리한다. (식사)")
        @Test
        void useMealCouponSerial() {
            Member sender = memberRepository.save(new Member(NEO_NAME, NEO_EMAIL, NEO_SOCIAL_ID, HUNI_IMAGE_URL));
            Member receiver = memberRepository.save(new Member(HOHO_NAME, HOHO_EMAIL, HOHO_SOCIAL_ID, SKRR_IMAGE_URL));

            CouponSerial notUsedSerial = couponSerialRepository.save(
                    new CouponSerial(SERIAL_1, sender.getId(), MEAL, NOT_USED, NEO_TITLE, NEO_MESSAGE));

            couponSerialService.use(receiver.getId(), new CouponSerialRequest(SERIAL_1));

            CouponSerial usedSerial = couponSerialRepository.findById(notUsedSerial.getId()).get();

            assertThat(usedSerial.getStatus()).isEqualTo(USED);
            assertThat(usedSerial.getCouponSerialType()).isEqualTo(MEAL);
            assertThat(usedSerial.getContent().getTitle()).isEqualTo(NEO_TITLE);
            assertThat(usedSerial.getContent().getMessage()).isEqualTo(NEO_MESSAGE);
        }

        @DisplayName("받는 이가 존재하지 않는 경우 예외를 발생한다.")
        @Test
        void notFoundMember() {
            Member sender = memberRepository.save(new Member(NEO_NAME, NEO_EMAIL, NEO_SOCIAL_ID, HUNI_IMAGE_URL));

            couponSerialRepository.save(
                    new CouponSerial(SERIAL_1, sender.getId(), COFFEE, NOT_USED, NEO_TITLE, NEO_MESSAGE));

            assertThatThrownBy(() -> couponSerialService.use(sender.getId() + 1L, new CouponSerialRequest(SERIAL_1)))
                    .isInstanceOf(InvalidMemberException.class)
                    .hasMessage("존재하지 않는 회원입니다.");
        }

        @DisplayName("시리얼 번호가 존재하지 않는 경우 예외를 발생한다.")
        @Test
        void notFoundSerial() {
            Member receiver = memberRepository.save(new Member(HOHO_NAME, HOHO_EMAIL, HOHO_SOCIAL_ID, SKRR_IMAGE_URL));
            memberRepository.save(new Member(NEO_NAME, NEO_EMAIL, NEO_SOCIAL_ID, HUNI_IMAGE_URL));

            assertThatThrownBy(() -> couponSerialService.use(receiver.getId(), new CouponSerialRequest("1234")))
                    .isInstanceOf(InvalidCouponSerialException.class)
                    .hasMessage("존재하지 않는 쿠폰 시리얼 번호입니다.");
        }

        @DisplayName("만료된 시리얼일 경우 예외를 발생한다.")
        @Test
        void expiration() {
            Member receiver = memberRepository.save(new Member(HOHO_NAME, HOHO_EMAIL, HOHO_SOCIAL_ID, SKRR_IMAGE_URL));
            Member sender = memberRepository.save(new Member(NEO_NAME, NEO_EMAIL, NEO_SOCIAL_ID, HUNI_IMAGE_URL));

            couponSerialRepository.save(
                    new CouponSerial(SERIAL_1, sender.getId(), COFFEE, USED, NEO_TITLE, NEO_MESSAGE));

            assertThatThrownBy(() -> couponSerialService.use(receiver.getId(), new CouponSerialRequest(SERIAL_1)))
                    .isInstanceOf(InvalidCouponSerialException.class)
                    .hasMessage("사용이 만료된 시리얼 번호입니다.");
        }
    }
}

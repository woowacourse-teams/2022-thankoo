package com.woowacourse.thankoo.coupon.application;

import static com.woowacourse.thankoo.common.fixtures.CouponFixture.MESSAGE;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.TITLE;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.TYPE;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_IMAGE_URL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.NEO_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.NEO_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.NEO_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_IMAGE_URL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_NAME;
import static com.woowacourse.thankoo.common.fixtures.SerialFixture.SERIAL_1;
import static com.woowacourse.thankoo.serial.domain.CouponSerialStatus.NOT_USED;
import static com.woowacourse.thankoo.serial.domain.CouponSerialType.COFFEE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.thankoo.common.annotations.ApplicationTest;
import com.woowacourse.thankoo.coupon.application.dto.ContentRequest;
import com.woowacourse.thankoo.coupon.application.dto.CouponRequest;
import com.woowacourse.thankoo.coupon.application.dto.CouponSerialRequest;
import com.woowacourse.thankoo.coupon.domain.Coupon;
import com.woowacourse.thankoo.coupon.domain.CouponRepository;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import com.woowacourse.thankoo.member.exception.InvalidMemberException;
import com.woowacourse.thankoo.serial.domain.CouponSerial;
import com.woowacourse.thankoo.serial.domain.CouponSerialRepository;
import com.woowacourse.thankoo.serial.exeption.InvalidCouponSerialException;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("CouponService 는 ")
@ApplicationTest
@ExtendWith(MockitoExtension.class)
class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CouponSerialRepository couponSerialRepository;

    @DisplayName("쿠폰을 저장할 때 ")
    @Nested
    class SaveCouponTest {

        @DisplayName("회원이 존재하면 정상적으로 저장한다.")
        @Test
        void save() {
            Member sender = memberRepository.save(new Member(HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, SKRR_IMAGE_URL));
            Member receiver = memberRepository.save(new Member(SKRR_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, SKRR_IMAGE_URL));
            couponService.saveAll(sender.getId(), new CouponRequest(List.of(receiver.getId()),
                    new ContentRequest(TYPE, TITLE, MESSAGE)));

            List<Coupon> coupons = couponRepository.findAll();

            assertThat(coupons).hasSize(1);
        }

        @DisplayName("회원들이 존재하면 정상적으로 저장한다.")
        @Test
        void saveAll() {
            Member sender = memberRepository.save(new Member(HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, SKRR_IMAGE_URL));
            Member receiver1 = memberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, SKRR_IMAGE_URL));
            Member receiver2 = memberRepository.save(new Member(SKRR_NAME, HOHO_EMAIL, HOHO_SOCIAL_ID, SKRR_IMAGE_URL));

            couponService.saveAll(sender.getId(), new CouponRequest(List.of(receiver1.getId(), receiver2.getId()),
                    new ContentRequest(TYPE, TITLE, MESSAGE)));

            List<Coupon> coupons = couponRepository.findAll();

            assertThat(coupons).hasSize(2);
        }

        @DisplayName("회원이 존재하지 않으면 예외가 발생한다.")
        @Test
        void saveInvalidMemberException() {
            Member sender = memberRepository.save(new Member(HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, SKRR_IMAGE_URL));

            assertThatThrownBy(() -> couponService.saveAll(sender.getId(), new CouponRequest(List.of(0L),
                    new ContentRequest(TYPE, TITLE, MESSAGE))))
                    .isInstanceOf(InvalidMemberException.class)
                    .hasMessage("존재하지 않는 회원입니다.");
        }
    }

    @DisplayName("시리얼 코드로 쿠폰을 저장할 때")
    @Nested
    class CreateCoupon {

        @DisplayName("회원이 존재하지 않으면 예외가 발생한다.")
        @Test
        void notFoundMember() {
            assertThatThrownBy(() -> couponService.saveWithSerialCode(1L, new CouponSerialRequest(SERIAL_1)))
                    .isInstanceOf(InvalidMemberException.class)
                    .hasMessage("존재하지 않는 회원입니다.");
        }

        @DisplayName("시리얼 코드가 존재하지 않으면 예외가 발생한다.")
        @Test
        void notFoundSerialCode() {
            Member member = memberRepository.save(new Member(HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, SKRR_IMAGE_URL));

            assertThatThrownBy(
                    () -> couponService.saveWithSerialCode(member.getId(), new CouponSerialRequest(SERIAL_1)))
                    .isInstanceOf(InvalidCouponSerialException.class)
                    .hasMessage("존재하지 않는 쿠폰 시리얼 번호입니다.");
        }

        @DisplayName("회원과 시리얼 코드가 존재하면 정상적으로 저장한다.")
        @Test
        void createCoupon() {
            Member member = memberRepository.save(new Member(HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, SKRR_IMAGE_URL));
            Member sender = memberRepository.save(new Member(NEO_NAME, NEO_EMAIL, NEO_SOCIAL_ID, HUNI_IMAGE_URL));
            couponSerialRepository.save(new CouponSerial(SERIAL_1, sender.getId(), COFFEE, NOT_USED));

            Long couponId = couponService.saveWithSerialCode(member.getId(), new CouponSerialRequest(SERIAL_1));

            Coupon savedCoupon = couponRepository.findById(couponId).get();

            assertThat(savedCoupon.getCouponContent().getTitle()).isEqualTo("neo가(이) 보내는 커피 쿠폰");
        }
    }
}


package com.woowacourse.thankoo.acceptance;

import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_NAME;
import static com.woowacourse.thankoo.common.fixtures.OAuthFixture.CODE_SKRR;
import static com.woowacourse.thankoo.common.fixtures.OAuthFixture.SKRR_TOKEN;
import static com.woowacourse.thankoo.common.fixtures.SerialFixture.SERIAL_1;
import static com.woowacourse.thankoo.serial.domain.CouponSerialType.COFFEE;

import com.woowacourse.thankoo.acceptance.builder.AuthenticationAssured;
import com.woowacourse.thankoo.acceptance.builder.CouponAssured;
import com.woowacourse.thankoo.authentication.presentation.dto.TokenResponse;
import com.woowacourse.thankoo.coupon.application.dto.CouponSerialRequest;
import com.woowacourse.thankoo.serial.domain.CouponSerial;
import com.woowacourse.thankoo.serial.domain.CouponSerialRepository;
import com.woowacourse.thankoo.serial.domain.CouponSerialStatus;
import com.woowacourse.thankoo.serial.domain.SerialCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

@DisplayName("CouponSerialAcceptance 는 ")
class CouponSerialAcceptanceTest extends AcceptanceTest {

    @DisplayName("회원이 로그인하고 ")
    @Nested
    class SignInAndTest {

        @DisplayName("시리얼 번호를 요청할 때 ")
        @Nested
        class CreateCouponWithSerial {

            @Autowired
            private CouponSerialRepository couponSerialRepository;

            @DisplayName("시리얼이 유효하면 쿠폰을 생성한다.")
            @Test
            void createCoupon() {
                TokenResponse memberToken = AuthenticationAssured.request()
                        .회원가입_한다(SKRR_TOKEN, SKRR_NAME)
                        .로그인_한다(CODE_SKRR)
                        .token();

                Long senderId = AuthenticationAssured.request()
                        .회원가입_한다(SKRR_TOKEN, SKRR_NAME)
                        .token()
                        .getMemberId();

                CouponSerial couponSerial = 쿠폰_시리얼을_생성한다(senderId, SERIAL_1);

                SerialCode serialCode = couponSerial.getSerialCode();
                CouponAssured.request()
                        .쿠폰_시리얼을_요청한다(memberToken.getAccessToken(), new CouponSerialRequest(serialCode.getValue()))
                        .response()
                        .status(HttpStatus.OK.value());
            }

            private CouponSerial 쿠폰_시리얼을_생성한다(Long memberId, String code) {
                return couponSerialRepository
                        .save(new CouponSerial(code, memberId, COFFEE, CouponSerialStatus.NOT_USED));
            }
        }
    }
}

package com.woowacourse.thankoo.acceptance;

import static com.woowacourse.thankoo.acceptance.builder.CouponAssured.잘못된_쿠폰_요청;
import static com.woowacourse.thankoo.acceptance.builder.CouponAssured.쿠폰_요청;
import static com.woowacourse.thankoo.acceptance.builder.OrganizationAssured.조직_번호;
import static com.woowacourse.thankoo.acceptance.builder.ReservationAssured.예약_요청;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.ALL;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.MESSAGE;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.MESSAGE_OVER;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.NOT_USED;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.TITLE;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.TITLE_OVER;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.TYPE;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.USED;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.USED_IMMEDIATELY;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.INVALID_TOKEN;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_NAME;
import static com.woowacourse.thankoo.common.fixtures.OAuthFixture.CODE_HUNI;
import static com.woowacourse.thankoo.common.fixtures.OAuthFixture.CODE_SKRR;
import static com.woowacourse.thankoo.common.fixtures.OAuthFixture.HOHO_TOKEN;
import static com.woowacourse.thankoo.common.fixtures.OAuthFixture.HUNI_TOKEN;
import static com.woowacourse.thankoo.common.fixtures.OAuthFixture.SKRR_TOKEN;
import static com.woowacourse.thankoo.common.fixtures.OrganizationFixture.ORGANIZATION_WOOWACOURSE_CODE;
import static com.woowacourse.thankoo.common.fixtures.ReservationFixture.ACCEPT;

import com.woowacourse.thankoo.acceptance.builder.AuthenticationAssured;
import com.woowacourse.thankoo.acceptance.builder.CouponAssured;
import com.woowacourse.thankoo.acceptance.builder.MeetingAssured;
import com.woowacourse.thankoo.acceptance.builder.OrganizationAssured;
import com.woowacourse.thankoo.acceptance.builder.ReservationAssured;
import com.woowacourse.thankoo.authentication.presentation.dto.TokenResponse;
import com.woowacourse.thankoo.common.fixtures.CouponFixture;
import com.woowacourse.thankoo.coupon.presentation.dto.CouponResponse;
import com.woowacourse.thankoo.organization.presentation.dto.OrganizationResponse;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@DisplayName("CouponAcceptance 는 ")
class CouponAcceptanceTest extends AcceptanceTest {

    @DisplayName("회원이 로그인하고 ")
    @Nested
    class SignInAndTest {

        @DisplayName("사용하지 않은 받은 쿠폰을 조회한다.")
        @Test
        void getReceivedCouponsNotUsed() {
            TokenResponse senderToken = AuthenticationAssured.request()
                    .회원가입_한다(SKRR_TOKEN, SKRR_NAME)
                    .로그인_한다(CODE_SKRR)
                    .response()
                    .body(TokenResponse.class);

            TokenResponse receiverToken = AuthenticationAssured.request()
                    .회원가입_한다(HOHO_TOKEN, HOHO_NAME)
                    .response()
                    .body(TokenResponse.class);

            기본_조직이_생성됨();
            OrganizationResponse organizationResponse = OrganizationAssured.request()
                    .조직에_참여한다(senderToken, 조직_번호(ORGANIZATION_WOOWACOURSE_CODE))
                    .조직에_참여한다(receiverToken, 조직_번호(ORGANIZATION_WOOWACOURSE_CODE))
                    .내_조직을_조회한다(senderToken)
                    .response()
                    .bodies(OrganizationResponse.class).get(0);

            Long organizationId = organizationResponse.getOrganizationId();

            List<CouponResponse> couponResponses = CouponAssured.request()
                    .쿠폰을_전송한다(organizationId, senderToken.getAccessToken(), 쿠폰_요청(receiverToken.getMemberId()))
                    .쿠폰을_전송한다(organizationId, senderToken.getAccessToken(), 쿠폰_요청(receiverToken.getMemberId()))
                    .쿠폰을_전송한다(organizationId, senderToken.getAccessToken(), 쿠폰_요청(receiverToken.getMemberId()))
                    .쿠폰을_전송한다(organizationId, senderToken.getAccessToken(), 쿠폰_요청(receiverToken.getMemberId()))
                    .받은_쿠폰을_조회한다(organizationId, receiverToken.getAccessToken(), CouponFixture.NOT_USED)
                    .response()
                    .bodies(CouponResponse.class);

            ReservationAssured.request()
                    .예약을_요청한다(receiverToken.getAccessToken(), 예약_요청(couponResponses.get(0), 1L))
                    .예약에_응답한다(senderToken.getAccessToken(), ACCEPT)
                    .done();

            ReservationAssured.request()
                    .예약을_요청한다(receiverToken.getAccessToken(), 예약_요청(couponResponses.get(1), 1L))
                    .예약에_응답한다(senderToken.getAccessToken(), ACCEPT)
                    .done();

            MeetingAssured.request()
                    .미팅을_조회한다(receiverToken.getAccessToken())
                    .미팅을_완료한다(senderToken.getAccessToken())
                    .response()
                    .status(HttpStatus.NO_CONTENT.value());

            CouponAssured.request()
                    .받은_쿠폰을_조회한다(organizationId, receiverToken.getAccessToken(), CouponFixture.NOT_USED)
                    .response()
                    .status(HttpStatus.OK.value())
                    .쿠폰이_조회됨(3);
        }

        @DisplayName("사용한 받은 쿠폰을 조회한다.")
        @Test
        void getReceivedCouponsUsed() {
            TokenResponse senderToken = AuthenticationAssured.request()
                    .회원가입_한다(SKRR_TOKEN, SKRR_NAME)
                    .로그인_한다(CODE_SKRR)
                    .response()
                    .body(TokenResponse.class);

            TokenResponse receiverToken = AuthenticationAssured.request()
                    .회원가입_한다(HOHO_TOKEN, HOHO_NAME)
                    .response()
                    .body(TokenResponse.class);

            기본_조직이_생성됨();
            OrganizationResponse organizationResponse = OrganizationAssured.request()
                    .조직에_참여한다(senderToken, 조직_번호(ORGANIZATION_WOOWACOURSE_CODE))
                    .조직에_참여한다(receiverToken, 조직_번호(ORGANIZATION_WOOWACOURSE_CODE))
                    .내_조직을_조회한다(senderToken)
                    .response()
                    .bodies(OrganizationResponse.class).get(0);

            Long organizationId = organizationResponse.getOrganizationId();

            List<CouponResponse> couponResponses = CouponAssured.request()
                    .쿠폰을_전송한다(organizationId, senderToken.getAccessToken(), 쿠폰_요청(receiverToken.getMemberId()))
                    .쿠폰을_전송한다(organizationId, senderToken.getAccessToken(), 쿠폰_요청(receiverToken.getMemberId()))
                    .쿠폰을_전송한다(organizationId, senderToken.getAccessToken(), 쿠폰_요청(receiverToken.getMemberId()))
                    .쿠폰을_전송한다(organizationId, senderToken.getAccessToken(), 쿠폰_요청(receiverToken.getMemberId()))
                    .받은_쿠폰을_조회한다(organizationId, receiverToken.getAccessToken(), CouponFixture.NOT_USED)
                    .response()
                    .bodies(CouponResponse.class);

            ReservationAssured.request()
                    .예약을_요청한다(receiverToken.getAccessToken(), 예약_요청(couponResponses.get(0), 1L))
                    .예약에_응답한다(senderToken.getAccessToken(), ACCEPT)
                    .done();

            ReservationAssured.request()
                    .예약을_요청한다(receiverToken.getAccessToken(), 예약_요청(couponResponses.get(1), 1L))
                    .예약에_응답한다(senderToken.getAccessToken(), ACCEPT)
                    .done();

            MeetingAssured.request()
                    .미팅을_조회한다(receiverToken.getAccessToken())
                    .미팅을_완료한다(senderToken.getAccessToken())
                    .response()
                    .status(HttpStatus.NO_CONTENT.value());

            CouponAssured.request()
                    .받은_쿠폰을_조회한다(organizationId, receiverToken.getAccessToken(), USED)
                    .response()
                    .status(HttpStatus.OK.value())
                    .쿠폰이_조회됨(1);
        }

        @DisplayName("모든 받은 쿠폰을 조회한다.")
        @Test
        void getReceivedCouponsAll() {
            TokenResponse senderToken = AuthenticationAssured.request()
                    .회원가입_한다(SKRR_TOKEN, SKRR_NAME)
                    .로그인_한다(CODE_SKRR)
                    .response()
                    .body(TokenResponse.class);

            TokenResponse receiverToken = AuthenticationAssured.request()
                    .회원가입_한다(HOHO_TOKEN, HOHO_NAME)
                    .response()
                    .body(TokenResponse.class);

            기본_조직이_생성됨();
            OrganizationResponse organizationResponse = OrganizationAssured.request()
                    .조직에_참여한다(senderToken, 조직_번호(ORGANIZATION_WOOWACOURSE_CODE))
                    .조직에_참여한다(receiverToken, 조직_번호(ORGANIZATION_WOOWACOURSE_CODE))
                    .내_조직을_조회한다(senderToken)
                    .response()
                    .bodies(OrganizationResponse.class).get(0);

            Long organizationId = organizationResponse.getOrganizationId();

            List<CouponResponse> couponResponses = CouponAssured.request()
                    .쿠폰을_전송한다(organizationId, senderToken.getAccessToken(), 쿠폰_요청(receiverToken.getMemberId()))
                    .쿠폰을_전송한다(organizationId, senderToken.getAccessToken(), 쿠폰_요청(receiverToken.getMemberId()))
                    .쿠폰을_전송한다(organizationId, senderToken.getAccessToken(), 쿠폰_요청(receiverToken.getMemberId()))
                    .쿠폰을_전송한다(organizationId, senderToken.getAccessToken(), 쿠폰_요청(receiverToken.getMemberId()))
                    .받은_쿠폰을_조회한다(organizationId, receiverToken.getAccessToken(), CouponFixture.NOT_USED)
                    .response()
                    .bodies(CouponResponse.class);

            ReservationAssured.request()
                    .예약을_요청한다(receiverToken.getAccessToken(), 예약_요청(couponResponses.get(0), 1L))
                    .예약에_응답한다(senderToken.getAccessToken(), ACCEPT)
                    .done();

            ReservationAssured.request()
                    .예약을_요청한다(receiverToken.getAccessToken(), 예약_요청(couponResponses.get(1), 1L))
                    .예약에_응답한다(senderToken.getAccessToken(), ACCEPT)
                    .done();

            CouponAssured.request()
                    .쿠폰을_즉시_사용한다(couponResponses.get(2).getCouponId(), receiverToken.getAccessToken());

            MeetingAssured.request()
                    .미팅을_조회한다(receiverToken.getAccessToken())
                    .미팅을_완료한다(senderToken.getAccessToken())
                    .response()
                    .status(HttpStatus.NO_CONTENT.value());

            CouponAssured.request()
                    .받은_쿠폰을_조회한다(organizationId, receiverToken.getAccessToken(), ALL)
                    .response()
                    .status(HttpStatus.OK.value())
                    .쿠폰이_조회됨(4);
        }

        @DisplayName("보낸 쿠폰을 조회한다.")
        @Test
        void getSentCoupons() {
            TokenResponse senderToken = AuthenticationAssured.request()
                    .회원가입_한다(SKRR_TOKEN, SKRR_NAME)
                    .로그인_한다(CODE_SKRR)
                    .response()
                    .body(TokenResponse.class);

            TokenResponse receiverToken = AuthenticationAssured.request()
                    .회원가입_한다(HOHO_TOKEN, HOHO_NAME)
                    .response()
                    .body(TokenResponse.class);

            기본_조직이_생성됨();
            OrganizationResponse organizationResponse = OrganizationAssured.request()
                    .조직에_참여한다(senderToken, 조직_번호(ORGANIZATION_WOOWACOURSE_CODE))
                    .조직에_참여한다(receiverToken, 조직_번호(ORGANIZATION_WOOWACOURSE_CODE))
                    .내_조직을_조회한다(senderToken)
                    .response()
                    .bodies(OrganizationResponse.class).get(0);

            Long organizationId = organizationResponse.getOrganizationId();

            CouponAssured.request()
                    .쿠폰을_전송한다(organizationId, senderToken.getAccessToken(), 쿠폰_요청(receiverToken.getMemberId()))
                    .쿠폰을_전송한다(organizationId, senderToken.getAccessToken(), 쿠폰_요청(receiverToken.getMemberId()))
                    .보낸_쿠폰을_조회한다(organizationId, senderToken.getAccessToken())
                    .response()
                    .status(HttpStatus.OK.value())
                    .쿠폰이_조회됨(2);
        }

        @DisplayName("회원이 쿠폰과 예약정보를 조회한다.")
        @Test
        void getCouponWithReservation() {
            TokenResponse senderToken = AuthenticationAssured.request()
                    .회원가입_한다(SKRR_TOKEN, SKRR_NAME)
                    .로그인_한다(CODE_SKRR)
                    .response()
                    .body(TokenResponse.class);

            TokenResponse receiverToken = AuthenticationAssured.request()
                    .회원가입_한다(HOHO_TOKEN, HOHO_NAME)
                    .response()
                    .body(TokenResponse.class);

            기본_조직이_생성됨();
            OrganizationResponse organizationResponse = OrganizationAssured.request()
                    .조직에_참여한다(senderToken, 조직_번호(ORGANIZATION_WOOWACOURSE_CODE))
                    .조직에_참여한다(receiverToken, 조직_번호(ORGANIZATION_WOOWACOURSE_CODE))
                    .내_조직을_조회한다(senderToken)
                    .response()
                    .bodies(OrganizationResponse.class).get(0);

            Long organizationId = organizationResponse.getOrganizationId();

            CouponResponse couponResponse = CouponAssured.request()
                    .쿠폰을_전송한다(organizationId, senderToken.getAccessToken(), 쿠폰_요청(receiverToken.getMemberId()))
                    .받은_쿠폰을_조회한다(organizationId, receiverToken.getAccessToken(), CouponFixture.NOT_USED)
                    .response()
                    .bodies(CouponResponse.class).get(0);

            ReservationAssured.request()
                    .예약을_요청한다(receiverToken.getAccessToken(), 예약_요청(couponResponse, 1L))
                    .done();

            CouponAssured
                    .request()
                    .쿠폰_단건_정보를_조회한다(couponResponse.getCouponId(), organizationId, receiverToken.getAccessToken())
                    .response()
                    .status(HttpStatus.OK.value())
                    .단건_쿠폰과_예약이_조회됨();
        }

        @DisplayName("회원이 쿠폰과 미팅정보를 조회한다.")
        @Test
        void getCouponWithMeeting() {
            TokenResponse senderToken = AuthenticationAssured.request()
                    .회원가입_한다(SKRR_TOKEN, SKRR_NAME)
                    .로그인_한다(CODE_SKRR)
                    .response()
                    .body(TokenResponse.class);

            TokenResponse receiverToken = AuthenticationAssured.request()
                    .회원가입_한다(HOHO_TOKEN, HOHO_NAME)
                    .response()
                    .body(TokenResponse.class);

            기본_조직이_생성됨();
            OrganizationResponse organizationResponse = OrganizationAssured.request()
                    .조직에_참여한다(senderToken, 조직_번호(ORGANIZATION_WOOWACOURSE_CODE))
                    .조직에_참여한다(receiverToken, 조직_번호(ORGANIZATION_WOOWACOURSE_CODE))
                    .내_조직을_조회한다(senderToken)
                    .response()
                    .bodies(OrganizationResponse.class).get(0);

            Long organizationId = organizationResponse.getOrganizationId();

            CouponResponse couponResponse = CouponAssured.request()
                    .쿠폰을_전송한다(organizationId, senderToken.getAccessToken(), 쿠폰_요청(receiverToken.getMemberId()))
                    .받은_쿠폰을_조회한다(organizationId, receiverToken.getAccessToken(), CouponFixture.NOT_USED)
                    .response()
                    .bodies(CouponResponse.class).get(0);

            ReservationAssured.request()
                    .예약을_요청한다(receiverToken.getAccessToken(), 예약_요청(couponResponse, 1L))
                    .예약에_응답한다(senderToken.getAccessToken(), ACCEPT)
                    .done();

            CouponAssured
                    .request()
                    .쿠폰_단건_정보를_조회한다(couponResponse.getCouponId(), organizationId, receiverToken.getAccessToken())
                    .response()
                    .status(HttpStatus.OK.value())
                    .단건_쿠폰과_미팅이_조회됨();
        }

        @DisplayName("회원의 보낸 쿠폰 받은 쿠폰 개수를 확인한다.")
        @Test
        void getCouponTotalCount() {
            TokenResponse token1 = AuthenticationAssured.request()
                    .회원가입_한다(SKRR_TOKEN, SKRR_NAME)
                    .로그인_한다(CODE_SKRR)
                    .response()
                    .body(TokenResponse.class);

            TokenResponse token2 = AuthenticationAssured.request()
                    .회원가입_한다(HOHO_TOKEN, HOHO_NAME)
                    .response()
                    .body(TokenResponse.class);

            기본_조직이_생성됨();
            OrganizationResponse organizationResponse = OrganizationAssured.request()
                    .조직에_참여한다(token1, 조직_번호(ORGANIZATION_WOOWACOURSE_CODE))
                    .조직에_참여한다(token2, 조직_번호(ORGANIZATION_WOOWACOURSE_CODE))
                    .내_조직을_조회한다(token1)
                    .response()
                    .bodies(OrganizationResponse.class).get(0);

            Long organizationId = organizationResponse.getOrganizationId();

            CouponAssured.request()
                    .쿠폰을_전송한다(organizationId, token1.getAccessToken(), 쿠폰_요청(token2.getMemberId()))
                    .쿠폰을_전송한다(organizationId, token1.getAccessToken(), 쿠폰_요청(token2.getMemberId()))
                    .쿠폰을_전송한다(organizationId, token2.getAccessToken(), 쿠폰_요청(token1.getMemberId()))
                    .주고_받은_쿠폰_개수를_조회한다(token1.getAccessToken())
                    .response()
                    .status(HttpStatus.OK.value())
                    .쿠폰_개수가_조회됨(2, 1);
        }

        @DisplayName("쿠폰을 보낼 때 ")
        @Nested
        class SendAndTest {

            @DisplayName("정상적으로 쿠폰을 보낸다.")
            @Test
            void sendCoupon() {
                TokenResponse senderToken = AuthenticationAssured.request()
                        .회원가입_한다(HUNI_TOKEN, HUNI_NAME)
                        .로그인_한다(CODE_HUNI)
                        .response()
                        .body(TokenResponse.class);

                TokenResponse receiverToken1 = AuthenticationAssured.request()
                        .회원가입_한다(SKRR_TOKEN, SKRR_NAME)
                        .response()
                        .body(TokenResponse.class);

                TokenResponse receiverToken2 = AuthenticationAssured.request()
                        .회원가입_한다(HOHO_TOKEN, HOHO_NAME)
                        .response()
                        .body(TokenResponse.class);

                기본_조직이_생성됨();
                OrganizationResponse organizationResponse = OrganizationAssured.request()
                        .조직에_참여한다(senderToken, 조직_번호(ORGANIZATION_WOOWACOURSE_CODE))
                        .조직에_참여한다(receiverToken1, 조직_번호(ORGANIZATION_WOOWACOURSE_CODE))
                        .조직에_참여한다(receiverToken2, 조직_번호(ORGANIZATION_WOOWACOURSE_CODE))
                        .내_조직을_조회한다(senderToken)
                        .response()
                        .bodies(OrganizationResponse.class).get(0);

                Long organizationId = organizationResponse.getOrganizationId();

                CouponAssured.request()
                        .쿠폰을_전송한다(organizationId, senderToken.getAccessToken(),
                                쿠폰_요청(receiverToken1.getMemberId(), receiverToken2.getMemberId()))
                        .response()
                        .status(HttpStatus.OK.value());
            }

            @DisplayName("자기 자신에게 보내면 실패한다.")
            @Test
            void sendByMySelfException() {
                TokenResponse senderToken = AuthenticationAssured.request()
                        .회원가입_한다(SKRR_TOKEN, SKRR_NAME)
                        .로그인_한다(CODE_SKRR)
                        .response()
                        .body(TokenResponse.class);

                기본_조직이_생성됨();
                OrganizationResponse organizationResponse = OrganizationAssured.request()
                        .조직에_참여한다(senderToken, 조직_번호(ORGANIZATION_WOOWACOURSE_CODE))
                        .내_조직을_조회한다(senderToken)
                        .response()
                        .bodies(OrganizationResponse.class).get(0);

                Long organizationId = organizationResponse.getOrganizationId();

                CouponAssured.request()
                        .쿠폰을_전송한다(organizationId, senderToken.getAccessToken(), 쿠폰_요청(senderToken.getMemberId()))
                        .response()
                        .status(HttpStatus.BAD_REQUEST.value());
            }

            @DisplayName("제목이 제약조건에 맞지 않을 경우 쿠폰 전송 실패한다.")
            @Test
            void sendCouponTitleException() {
                TokenResponse senderToken = AuthenticationAssured.request()
                        .회원가입_한다(SKRR_TOKEN, SKRR_NAME)
                        .로그인_한다(CODE_SKRR)
                        .response()
                        .body(TokenResponse.class);

                TokenResponse receiverToken = AuthenticationAssured.request()
                        .회원가입_한다(HOHO_TOKEN, HOHO_NAME)
                        .response()
                        .body(TokenResponse.class);

                기본_조직이_생성됨();
                OrganizationResponse organizationResponse = OrganizationAssured.request()
                        .조직에_참여한다(senderToken, 조직_번호(ORGANIZATION_WOOWACOURSE_CODE))
                        .조직에_참여한다(receiverToken, 조직_번호(ORGANIZATION_WOOWACOURSE_CODE))
                        .내_조직을_조회한다(senderToken)
                        .response()
                        .bodies(OrganizationResponse.class).get(0);

                Long organizationId = organizationResponse.getOrganizationId();

                CouponAssured.request()
                        .쿠폰을_전송한다(organizationId, senderToken.getAccessToken(),
                                잘못된_쿠폰_요청(TYPE, TITLE_OVER, MESSAGE, receiverToken.getMemberId()))
                        .response()
                        .status(HttpStatus.BAD_REQUEST.value());
            }

            @DisplayName("내용이 제약조건에 맞지 않을 경우 쿠폰 전송 실패한다.")
            @Test
            void sendCouponMessageException() {
                TokenResponse senderToken = AuthenticationAssured.request()
                        .회원가입_한다(SKRR_TOKEN, SKRR_NAME)
                        .로그인_한다(CODE_SKRR)
                        .token();

                TokenResponse receiverToken = AuthenticationAssured.request()
                        .회원가입_한다(HOHO_TOKEN, HOHO_NAME)
                        .token();

                기본_조직이_생성됨();
                OrganizationResponse organizationResponse = OrganizationAssured.request()
                        .조직에_참여한다(senderToken, 조직_번호(ORGANIZATION_WOOWACOURSE_CODE))
                        .조직에_참여한다(receiverToken, 조직_번호(ORGANIZATION_WOOWACOURSE_CODE))
                        .내_조직을_조회한다(senderToken)
                        .response()
                        .bodies(OrganizationResponse.class).get(0);

                Long organizationId = organizationResponse.getOrganizationId();

                CouponAssured.request()
                        .쿠폰을_전송한다(organizationId, senderToken.getAccessToken(),
                                잘못된_쿠폰_요청(TYPE, TITLE, MESSAGE_OVER, receiverToken.getMemberId()))
                        .response()
                        .status(HttpStatus.BAD_REQUEST.value());
            }
        }

        @DisplayName("쿠폰을 즉시 사용한다.")
        @Test
        void useImmediately() {
            TokenResponse senderToken = AuthenticationAssured.request()
                    .회원가입_한다(SKRR_TOKEN, SKRR_NAME)
                    .로그인_한다(CODE_SKRR)
                    .token();

            TokenResponse receiverToken = AuthenticationAssured.request()
                    .회원가입_한다(HOHO_TOKEN, HOHO_NAME)
                    .token();

            기본_조직이_생성됨();
            OrganizationResponse organizationResponse = OrganizationAssured.request()
                    .조직에_참여한다(senderToken, 조직_번호(ORGANIZATION_WOOWACOURSE_CODE))
                    .조직에_참여한다(receiverToken, 조직_번호(ORGANIZATION_WOOWACOURSE_CODE))
                    .내_조직을_조회한다(senderToken)
                    .response()
                    .bodies(OrganizationResponse.class).get(0);

            Long organizationId = organizationResponse.getOrganizationId();

            CouponResponse couponResponse = CouponAssured.request()
                    .쿠폰을_전송한다(organizationId, senderToken.getAccessToken(), 쿠폰_요청(receiverToken.getMemberId()))
                    .받은_쿠폰을_조회한다(organizationId, receiverToken.getAccessToken(), NOT_USED)
                    .response()
                    .bodies(CouponResponse.class).get(0);

            Long couponId = couponResponse.getCouponId();

            CouponAssured.request()
                    .쿠폰을_즉시_사용한다(couponId, receiverToken.getAccessToken())
                    .받은_쿠폰을_조회한다(organizationId, receiverToken.getAccessToken(), USED)
                    .response()
                    .쿠폰의_상태가_조회됨(couponId, USED_IMMEDIATELY);
        }
    }

    @DisplayName("로그인 하지 않고 ")
    @Nested
    class NotSignInAndTest {

        @DisplayName("쿠폰을 전송하면 실패한다.")
        @Test
        void sendCouponInvalidToken() {
            TokenResponse receiverToken = AuthenticationAssured.request()
                    .회원가입_한다(HOHO_TOKEN, HOHO_NAME)
                    .token();

            기본_조직이_생성됨();
            OrganizationResponse organizationResponse = OrganizationAssured.request()
                    .조직에_참여한다(receiverToken, 조직_번호(ORGANIZATION_WOOWACOURSE_CODE))
                    .내_조직을_조회한다(receiverToken)
                    .response()
                    .bodies(OrganizationResponse.class).get(0);

            Long organizationId = organizationResponse.getOrganizationId();

            CouponAssured.request()
                    .쿠폰을_전송한다(organizationId, INVALID_TOKEN, 쿠폰_요청(receiverToken.getMemberId()))
                    .response()
                    .status(HttpStatus.UNAUTHORIZED.value());
        }

        @DisplayName("받은 쿠폰을 조회하면 실패한다.")
        @Test
        void getCouponsInvalidToken() {
            TokenResponse senderToken = AuthenticationAssured.request()
                    .회원가입_한다(SKRR_TOKEN, SKRR_NAME)
                    .로그인_한다(CODE_SKRR)
                    .token();

            TokenResponse receiverToken = AuthenticationAssured.request()
                    .회원가입_한다(HOHO_TOKEN, HOHO_NAME)
                    .token();

            기본_조직이_생성됨();
            OrganizationResponse organizationResponse = OrganizationAssured.request()
                    .조직에_참여한다(senderToken, 조직_번호(ORGANIZATION_WOOWACOURSE_CODE))
                    .조직에_참여한다(receiverToken, 조직_번호(ORGANIZATION_WOOWACOURSE_CODE))
                    .내_조직을_조회한다(senderToken)
                    .response()
                    .bodies(OrganizationResponse.class).get(0);

            Long organizationId = organizationResponse.getOrganizationId();

            CouponAssured.request()
                    .쿠폰을_전송한다(organizationId, senderToken.getAccessToken(), 쿠폰_요청(receiverToken.getMemberId()))
                    .받은_쿠폰을_조회한다(organizationId, INVALID_TOKEN, CouponFixture.NOT_USED)
                    .response()
                    .status(HttpStatus.UNAUTHORIZED.value());
        }
    }
}

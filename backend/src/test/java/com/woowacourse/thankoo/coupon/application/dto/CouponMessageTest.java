package com.woowacourse.thankoo.coupon.application.dto;

import static com.woowacourse.thankoo.common.fixtures.CouponFixture.COFFEE_COUPON_CONTENT;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.MEAL_COUPON_CONTENT;
import static com.woowacourse.thankoo.common.fixtures.CouponFixture.TITLE;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_EMAIL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.thankoo.alarm.support.Message;
import com.woowacourse.thankoo.member.domain.Name;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("CouponMessage 는")
class CouponMessageTest {

    @DisplayName("커피 쿠폰 메시지를 생성한다.")
    @Test
    void createCoffee() {
        Message message = CouponMessage.of(new Name(HOHO_NAME), List.of(HUNI_EMAIL), COFFEE_COUPON_CONTENT);

        assertAll(
                () -> assertThat(message.getTitle()).contains("커피 쿠폰이 도착했어요."),
                () -> assertThat(message.getEmails()).containsExactly(HUNI_EMAIL),
                () -> assertThat(message.getContents()).contains(
                        "보내는 이 : " + HOHO_NAME, "제목 : " + TITLE, "쿠폰 종류 : 커피☕")
        );
    }


    @DisplayName("식사 쿠폰 메시지를 생성한다.")
    @Test
    void createMeal() {
        Message message = CouponMessage.of(new Name(HOHO_NAME), List.of(HUNI_EMAIL), MEAL_COUPON_CONTENT);

        assertAll(
                () -> assertThat(message.getTitle()).contains("커피 쿠폰이 도착했어요."),
                () -> assertThat(message.getEmails()).containsExactly(HUNI_EMAIL),
                () -> assertThat(message.getContents()).contains(
                        "보내는 이 : " + HOHO_NAME, "제목 : 호호의 식사쿠폰", "쿠폰 종류 : 식사\uD83C\uDF54")
        );
    }
}

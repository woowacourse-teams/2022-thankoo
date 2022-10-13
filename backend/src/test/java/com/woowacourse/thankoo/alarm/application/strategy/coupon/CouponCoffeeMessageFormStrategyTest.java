package com.woowacourse.thankoo.alarm.application.strategy.coupon;

import static com.woowacourse.thankoo.common.domain.AlarmSpecification.*;
import static com.woowacourse.thankoo.common.fixtures.AlarmFixture.ROOT_LINK;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_IMAGE_URL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.thankoo.alarm.application.dto.Message;
import com.woowacourse.thankoo.alarm.domain.Alarm;
import com.woowacourse.thankoo.alarm.exception.InvalidAlarmException;
import com.woowacourse.thankoo.common.annotations.ApplicationTest;
import com.woowacourse.thankoo.common.domain.AlarmSpecification;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("CouponCoffeeMessageFormStrategy 는 ")
@ApplicationTest
class CouponCoffeeMessageFormStrategyTest {

    private static final String COFFEE_PRETEXT = "\uD83D\uDC8C 커피 쿠폰이 도착했어요.";  // 💌
    private static final String COUPON_TITLE = "널 좋아해";
    private static final String COFFEE_TYPE = "커피☕";
    private static final Long ORGANIZATION_ID = 1L;

    @Autowired
    private CouponCoffeeMessageFormStrategy couponCoffeeMessageFormStrategy;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("메시지 포맷을 만든다.")
    @Test
    void createFormat() {
        Member lala = memberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, SKRR_IMAGE_URL));
        Member hoho = memberRepository.save(new Member(HOHO_NAME, HOHO_EMAIL, HOHO_SOCIAL_ID, SKRR_IMAGE_URL));
        Member huni = memberRepository.save(new Member(HUNI_NAME, HUNI_EMAIL, HUNI_EMAIL, SKRR_IMAGE_URL));

        Alarm alarm = Alarm.create(
                new AlarmSpecification(COUPON_SENT_COFFEE, ORGANIZATION_ID, List.of(hoho.getId(), huni.getId()),
                        List.of(String.valueOf(lala.getId()), COUPON_TITLE, "coffee")));
        Message message = couponCoffeeMessageFormStrategy.createFormat(alarm);

        assertAll(
                () -> assertThat(message.getEmails()).containsExactly(hoho.getEmail().getValue(),
                        huni.getEmail().getValue()),
                () -> assertThat(message.getTitle()).isEqualTo(COFFEE_PRETEXT),
                () -> assertThat(message.getTitleLink()).isEqualTo(ROOT_LINK + ORGANIZATION_ID),
                () -> assertThat(message.getContents()).containsExactly(
                        "보내는 이 : lala",
                        "제목 : 널 좋아해",
                        "쿠폰 종류 : 커피☕"
                )
        );
    }

    @DisplayName("Coupon 메시지 형태가 맞지 않을 경우 예외가 발생한다.")
    @Test
    void createFormatException() {
        Member lala = memberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, SKRR_IMAGE_URL));
        Member hoho = memberRepository.save(new Member(HOHO_NAME, HOHO_EMAIL, HOHO_SOCIAL_ID, SKRR_IMAGE_URL));
        Member huni = memberRepository.save(new Member(HUNI_NAME, HUNI_EMAIL, HUNI_EMAIL, SKRR_IMAGE_URL));

        Alarm alarm = Alarm.create(
                new AlarmSpecification(COUPON_SENT_COFFEE, ORGANIZATION_ID, List.of(hoho.getId(), huni.getId()),
                        List.of(String.valueOf(lala.getId()), COUPON_TITLE)));

        assertThatThrownBy(
                () -> couponCoffeeMessageFormStrategy.createFormat(alarm))
                .isInstanceOf(InvalidAlarmException.class)
                .hasMessage("잘못된 알람 형식입니다.");
    }

    @DisplayName("올바른 SenderId가 들어있지 않은 경우 예외가 발생한다.")
    @Test
    void senderIdException() {
        Member hoho = memberRepository.save(new Member(HOHO_NAME, HOHO_EMAIL, HOHO_SOCIAL_ID, SKRR_IMAGE_URL));
        Member huni = memberRepository.save(new Member(HUNI_NAME, HUNI_EMAIL, HUNI_EMAIL, SKRR_IMAGE_URL));

        Alarm alarm = Alarm.create(
                new AlarmSpecification(COUPON_SENT_COFFEE, ORGANIZATION_ID, List.of(hoho.getId(), huni.getId()),
                        List.of("a", COUPON_TITLE, COFFEE_TYPE)));

        assertThatThrownBy(
                () -> couponCoffeeMessageFormStrategy.createFormat(alarm))
                .isInstanceOf(InvalidAlarmException.class)
                .hasMessage("잘못된 알람 형식입니다.");
    }
}

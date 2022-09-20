package com.woowacourse.thankoo.alarm.application;

import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_IMAGE_URL;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.woowacourse.thankoo.alarm.exception.InvalidAlarmException;
import com.woowacourse.thankoo.common.annotations.ApplicationTest;
import com.woowacourse.thankoo.common.domain.AlarmSpecification;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("AlarmService 는 ")
@ApplicationTest
class AlarmServiceTest {

    @Autowired
    private AlarmService alarmService;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("알람을 보낼 때 ")
    @Nested
    class SendTest {

        @DisplayName("알람 스펙이 맞지 않으면 실패한다.")
        @Test
        void specificationFailed() {
            Member lala = memberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, SKRR_IMAGE_URL));
            Member hoho = memberRepository.save(new Member(HOHO_NAME, HOHO_EMAIL, HOHO_SOCIAL_ID, SKRR_IMAGE_URL));
            Member huni = memberRepository.save(new Member(HUNI_NAME, HUNI_EMAIL, HUNI_EMAIL, SKRR_IMAGE_URL));

            AlarmSpecification alarmSpecification = new AlarmSpecification(AlarmSpecification.COUPON_SENT_COFFEE,
                    List.of(hoho.getId(), huni.getId()),
                    List.of(String.valueOf(lala.getId()), "coffee"));

            assertThatThrownBy(() -> alarmService.send(alarmSpecification))
                    .isInstanceOf(InvalidAlarmException.class)
                    .hasMessage("잘못된 알람 형식입니다.");
        }

        @DisplayName("정상적으로 알람을 보낸다.")
        @Test
        void success() {
            Member lala = memberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, SKRR_IMAGE_URL));
            Member hoho = memberRepository.save(new Member(HOHO_NAME, HOHO_EMAIL, HOHO_SOCIAL_ID, SKRR_IMAGE_URL));
            Member huni = memberRepository.save(new Member(HUNI_NAME, HUNI_EMAIL, HUNI_EMAIL, SKRR_IMAGE_URL));

            AlarmSpecification alarmSpecification = new AlarmSpecification(AlarmSpecification.COUPON_SENT_COFFEE,
                    List.of(hoho.getId(), huni.getId()),
                    List.of(String.valueOf(lala.getId()), "널 좋아해", "coffee"));

            assertDoesNotThrow(() -> alarmService.send(alarmSpecification));
        }
    }
}

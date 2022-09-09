package com.woowacourse.thankoo.alarm.infrastructure.integrate;

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

import com.woowacourse.thankoo.alarm.exception.InvalidAlarmException;
import com.woowacourse.thankoo.common.annotations.ApplicationTest;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("AlarmMemberInformationClient 는 ")
@ApplicationTest
class AlarmMemberInformationClientTest {

    @Autowired
    private AlarmMemberInformationClient alarmMemberInformationClient;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("받는 이의 이메일을 가져온다.")
    @Test
    void getReceiverEmails() {
        Member lala = memberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, SKRR_IMAGE_URL));
        Member hoho = memberRepository.save(new Member(HOHO_NAME, HOHO_EMAIL, HOHO_SOCIAL_ID, SKRR_IMAGE_URL));
        Member huni = memberRepository.save(new Member(HUNI_NAME, HUNI_EMAIL, HUNI_EMAIL, SKRR_IMAGE_URL));

        assertThat(alarmMemberInformationClient.getReceiverEmails(List.of(lala.getId(), hoho.getId(), huni.getId())))
                .containsExactly(LALA_EMAIL, HOHO_EMAIL, HUNI_EMAIL);
    }

    @DisplayName("보내는 이의 이름을 가져올 때 ")
    @Nested
    class GetSenderNameTest {

        @DisplayName("올바른 요청일 경우 성공한다.")
        @Test
        void success() {
            Member lala = memberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, SKRR_IMAGE_URL));

            assertThat(alarmMemberInformationClient.getSenderName(String.valueOf(lala.getId()))).isEqualTo(LALA_NAME);
        }

        @DisplayName("id를 String으로 보내지 않은 경우 실패한다.")
        @Test
        void idNumberFormatFailed() {
            assertThatThrownBy(() -> alarmMemberInformationClient.getSenderName("a"))
                    .isInstanceOf(InvalidAlarmException.class)
                    .hasMessage("잘못된 알람 형식입니다.");
        }
    }
}

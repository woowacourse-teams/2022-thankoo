package com.woowacourse.thankoo.alarm.application.strategy.reservation;

import static com.woowacourse.thankoo.common.fixtures.AlarmFixture.ROOT_LINK;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_IMAGE_URL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.thankoo.alarm.application.dto.Message;
import com.woowacourse.thankoo.alarm.domain.Alarm;
import com.woowacourse.thankoo.common.annotations.ApplicationTest;
import com.woowacourse.thankoo.common.domain.AlarmSpecification;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import java.text.MessageFormat;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("ReservationRepliedMessageFormStrategy 는 ")
@ApplicationTest
class ReservationRepliedMessageFormStrategyTest {

    private static final String PRETEXT = "\uD83D\uDC7B {0}님이 예약 요청에 응답했어요.";
    private static final String COUPON_TITLE = "널 좋아해";

    @Autowired
    private ReservationRepliedMessageFormStrategy reservationRepliedMessageFormStrategy;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("거절 메시지 포맷을 만든다.")
    @Test
    void createFormatDeny() {
        Member lala = memberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, SKRR_IMAGE_URL));
        Member hoho = memberRepository.save(new Member(HOHO_NAME, HOHO_EMAIL, HOHO_SOCIAL_ID, SKRR_IMAGE_URL));

        Alarm alarm = Alarm.create(
                new AlarmSpecification(AlarmSpecification.RESERVATION_REPLIED, List.of(hoho.getId()),
                        List.of(String.valueOf(lala.getId()), COUPON_TITLE, "DENY")));

        Message message = reservationRepliedMessageFormStrategy.createFormat(alarm);
        assertAll(
                () -> assertThat(message.getEmails()).containsExactly(hoho.getEmail().getValue()),
                () -> assertThat(message.getTitle()).isEqualTo(MessageFormat.format(PRETEXT, "lala")),
                () -> assertThat(message.getTitleLink()).isEqualTo(ROOT_LINK),
                () -> assertThat(message.getContents()).containsExactly(
                        "쿠폰 : 널 좋아해",
                        "예약 상태 : 거절\uD83D\uDE05"
                )
        );
    }

    @DisplayName("수락 메시지 포맷을 만든다.")
    @Test
    void createFormatAccept() {
        Member lala = memberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, SKRR_IMAGE_URL));
        Member hoho = memberRepository.save(new Member(HOHO_NAME, HOHO_EMAIL, HOHO_SOCIAL_ID, SKRR_IMAGE_URL));

        Alarm alarm = Alarm.create(
                new AlarmSpecification(AlarmSpecification.RESERVATION_REPLIED, List.of(hoho.getId()),
                        List.of(String.valueOf(lala.getId()), COUPON_TITLE, "ACCEPT")));

        Message message = reservationRepliedMessageFormStrategy.createFormat(alarm);
        assertAll(
                () -> assertThat(message.getEmails()).containsExactly(hoho.getEmail().getValue()),
                () -> assertThat(message.getTitle()).isEqualTo(MessageFormat.format(PRETEXT, "lala")),
                () -> assertThat(message.getTitleLink()).isEqualTo(ROOT_LINK + "/meetings"),
                () -> assertThat(message.getContents()).containsExactly(
                        "쿠폰 : 널 좋아해",
                        "예약 상태 : 승인\uD83E\uDD70"
                )
        );
    }
}

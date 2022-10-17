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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("ReservationSentMessageFormStrategy 는 ")
@ApplicationTest
class ReservationSentMessageFormStrategyTest {

    private static final String PRETEXT = "\uD83D\uDC9D 예약 요청이 도착했어요.";
    private static final String COUPON_TITLE = "널 좋아해";
    private static final Long ORGANIZATION_ID = 1L;

    @Autowired
    private ReservationSentMessageFormStrategy reservationSentMessageFormStrategy;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("메시지 포맷을 만든다.")
    @Test
    void createFormat() {
        Member lala = memberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, SKRR_IMAGE_URL));
        Member hoho = memberRepository.save(new Member(HOHO_NAME, HOHO_EMAIL, HOHO_SOCIAL_ID, SKRR_IMAGE_URL));

        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        Alarm alarm = Alarm.create(
                new AlarmSpecification(AlarmSpecification.RESERVATION_SENT, ORGANIZATION_ID, List.of(hoho.getId()),
                        List.of(String.valueOf(lala.getId()), COUPON_TITLE, dateTime)));

        Message message = reservationSentMessageFormStrategy.createFormat(alarm);
        assertAll(
                () -> assertThat(message.getEmails()).containsExactly(hoho.getEmail().getValue()),
                () -> assertThat(message.getTitle()).isEqualTo(PRETEXT),
                () -> assertThat(message.getTitleLink()).isEqualTo(ROOT_LINK + ORGANIZATION_ID + "/reservations"),
                () -> assertThat(message.getContents()).containsExactly(
                        "요청자 : lala",
                        "쿠폰 : 널 좋아해",
                        "예약 요청일 : " + dateTime
                )
        );
    }
}

package com.woowacourse.thankoo.alarm.application.strategy;

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
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("HeartMessageFormStrategy 는 ")
@ApplicationTest
class HeartMessageFormStrategyTest {

    private static final Long ORGANIZATION_ID = 1L;

    @Autowired
    private HeartMessageFormStrategy heartMessageFormStrategy;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("메시지 포맷을 만든다.")
    @Test
    void createFormat() {
        Member lala = memberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, SKRR_IMAGE_URL));
        Member hoho = memberRepository.save(new Member(HOHO_NAME, HOHO_EMAIL, HOHO_SOCIAL_ID, SKRR_IMAGE_URL));

        Alarm alarm = Alarm.create(
                new AlarmSpecification(AlarmSpecification.RESERVATION_CANCELED, ORGANIZATION_ID, List.of(hoho.getId()),
                        List.of(String.valueOf(lala.getId()), String.valueOf(2))));

        Message message = heartMessageFormStrategy.createFormat(alarm);
        assertAll(
                () -> assertThat(message.getEmails()).containsExactly(hoho.getEmail().getValue()),
                () -> assertThat(message.getTitle()).isEqualTo("lala님이 당신에게 마음을 2번 보냈어요 ❤️"),
                () -> assertThat(message.getTitleLink()).isEqualTo(ROOT_LINK + ORGANIZATION_ID + "/hearts")
        );
    }
}

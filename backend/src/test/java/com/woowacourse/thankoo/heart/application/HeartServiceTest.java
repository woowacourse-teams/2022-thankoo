package com.woowacourse.thankoo.heart.application;

import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_IMAGE_URL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_SOCIAL_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.thankoo.common.annotations.ApplicationTest;
import com.woowacourse.thankoo.heart.application.dto.HeartRequest;
import com.woowacourse.thankoo.heart.domain.HeartRepository;
import com.woowacourse.thankoo.heart.exception.InvalidHeartException;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("HeartService 는 ")
@ApplicationTest
class HeartServiceTest {

    @Autowired
    private HeartService heartService;

    @Autowired
    private HeartRepository heartRepository;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("마음을 보낼 때 ")
    @Nested
    class SendAndTest {

        @DisplayName("정상적인 요청일 경우 성공한다.")
        @Test
        void send() {
            Member huni = memberRepository.save(new Member(HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, SKRR_IMAGE_URL));
            Member skrr = memberRepository.save(new Member(SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, SKRR_IMAGE_URL));

            heartService.send(huni.getId(), new HeartRequest(skrr.getId()));
            assertThat(heartRepository.findBySenderIdAndReceiverId(huni.getId(), skrr.getId()).get()).isNotNull();
        }

        @DisplayName("상대가 마음을 보내면 내가 보낼 수 있다.")
        @Test
        void sendPlusCount() {
            Member huni = memberRepository.save(new Member(HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, SKRR_IMAGE_URL));
            Member skrr = memberRepository.save(new Member(SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, SKRR_IMAGE_URL));

            heartService.send(huni.getId(), new HeartRequest(skrr.getId()));
            heartService.send(skrr.getId(), new HeartRequest(huni.getId()));
            assertThat(
                    heartRepository.findBySenderIdAndReceiverId(huni.getId(), skrr.getId()).get().isLast()).isFalse();
        }

        @DisplayName("연달아 두 번 보낼 경우 실패한다.")
        @Test
        void doubleSendFailed() {
            Member huni = memberRepository.save(new Member(HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, SKRR_IMAGE_URL));
            Member skrr = memberRepository.save(new Member(SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, SKRR_IMAGE_URL));

            heartService.send(huni.getId(), new HeartRequest(skrr.getId()));
            heartService.send(skrr.getId(), new HeartRequest(huni.getId()));
            assertThatThrownBy(() -> heartService.send(skrr.getId(), new HeartRequest(huni.getId())))
                    .isInstanceOf(InvalidHeartException.class)
                    .hasMessage("마음을 보낼 수 없습니다.");
        }
    }
}

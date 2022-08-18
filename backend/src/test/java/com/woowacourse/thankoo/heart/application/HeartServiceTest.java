package com.woowacourse.thankoo.heart.application;

import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.IMAGE_URL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_SOCIAL_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.thankoo.common.annotations.ApplicationTest;
import com.woowacourse.thankoo.heart.application.dto.HeartRequest;
import com.woowacourse.thankoo.heart.domain.HeartRepository;
import com.woowacourse.thankoo.heart.exception.InvalidHeartException;
import com.woowacourse.thankoo.heart.presentation.dto.HeartResponse;
import com.woowacourse.thankoo.heart.presentation.dto.HeartResponses;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import java.util.List;
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
            Member huni = memberRepository.save(new Member(HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, IMAGE_URL));
            Member skrr = memberRepository.save(new Member(SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, IMAGE_URL));

            heartService.send(huni.getId(), new HeartRequest(skrr.getId()));
            assertThat(heartRepository.findBySenderIdAndReceiverId(huni.getId(), skrr.getId()).get()).isNotNull();
        }

        @DisplayName("상대가 마음을 보내면 내가 보낼 수 있다.")
        @Test
        void sendPlusCount() {
            Member huni = memberRepository.save(new Member(HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, IMAGE_URL));
            Member skrr = memberRepository.save(new Member(SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, IMAGE_URL));

            heartService.send(huni.getId(), new HeartRequest(skrr.getId()));
            heartService.send(skrr.getId(), new HeartRequest(huni.getId()));
            assertThat(
                    heartRepository.findBySenderIdAndReceiverId(huni.getId(), skrr.getId()).get().isLast()).isFalse();
        }

        @DisplayName("연달아 두 번 보낼 경우 실패한다.")
        @Test
        void doubleSendFailed() {
            Member huni = memberRepository.save(new Member(HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, IMAGE_URL));
            Member skrr = memberRepository.save(new Member(SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, IMAGE_URL));

            heartService.send(huni.getId(), new HeartRequest(skrr.getId()));
            heartService.send(skrr.getId(), new HeartRequest(huni.getId()));
            assertThatThrownBy(() -> heartService.send(skrr.getId(), new HeartRequest(huni.getId())))
                    .isInstanceOf(InvalidHeartException.class)
                    .hasMessage("마음을 보낼 수 없습니다.");
        }
    }

    @DisplayName("연달아 보낼 수 없는 보낸 마음과 응답할 수 있는 받은 마음을 모두 조회한다.")
    @Test
    void getEachHeartsLast() {
        Member huni = memberRepository.save(new Member(HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, IMAGE_URL));
        Member skrr = memberRepository.save(new Member(SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, IMAGE_URL));
        Member lala = memberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, IMAGE_URL));
        Member hoho = memberRepository.save(new Member(HOHO_NAME, HOHO_EMAIL, HOHO_SOCIAL_ID, IMAGE_URL));

        heartService.send(skrr.getId(), new HeartRequest(huni.getId()));
        heartService.send(lala.getId(), new HeartRequest(huni.getId()));
        heartService.send(hoho.getId(), new HeartRequest(huni.getId()));

        heartService.send(huni.getId(), new HeartRequest(lala.getId()));

        HeartResponses heartResponses = heartService.getEachHeartsLast(huni.getId());
        List<HeartResponse> sentHearts = heartResponses.getSent();
        List<HeartResponse> receivedHearts = heartResponses.getReceived();
        assertAll(
                () -> assertThat(sentHearts).hasSize(1),
                () -> assertThat(sentHearts).extracting("receiverId")
                        .containsExactly(lala.getId()),
                () -> assertThat(receivedHearts).hasSize(2),
                () -> assertThat(receivedHearts).extracting("senderId")
                        .containsExactly(skrr.getId(), hoho.getId())
        );
    }
}

package com.woowacourse.thankoo.heart.domain;

import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.IMAGE_URL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_SOCIAL_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.thankoo.heart.exception.InvalidHeartException;
import com.woowacourse.thankoo.member.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Heart 는 ")
class HeartTest {

    @DisplayName("첫 마음을 보낸다")
    @Test
    void start() {
        Member huni = new Member(HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, IMAGE_URL);
        Member skrr = new Member(SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, IMAGE_URL);
        Heart heart = Heart.start(huni.getId(), skrr.getId());
        assertAll(
                () -> assertThat(heart.isFinal()).isTrue(),
                () -> assertThat(heart.getCount()).isEqualTo(1)
        );
    }

    @DisplayName("첫 응답으로 마음을 보낸다")
    @Test
    void firstReply() {
        Member huni = new Member(HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, IMAGE_URL);
        Member skrr = new Member(SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, IMAGE_URL);
        Heart heart = Heart.start(huni.getId(), skrr.getId());
        Heart reply = Heart.firstReply(skrr.getId(), huni.getId(), heart);
        assertAll(
                () -> assertThat(heart.isFinal()).isFalse(),
                () -> assertThat(reply.isFinal()).isTrue(),
                () -> assertThat(heart.getCount()).isEqualTo(1),
                () -> assertThat(reply.getCount()).isEqualTo(1)
        );
    }

    @DisplayName("마음을 보낼 때 ")
    @Nested
    class SendAndTest {

        @DisplayName("현재 마음이 마지막일 경우 실패한다.")
        @Test
        void sendFinalException() {
            Member huni = new Member(HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, IMAGE_URL);
            Member skrr = new Member(SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, IMAGE_URL);
            Heart heart = Heart.start(huni.getId(), skrr.getId());
            Heart reply = Heart.firstReply(skrr.getId(), huni.getId(), heart);
            assertThatThrownBy(() -> reply.send(heart))
                    .isInstanceOf(InvalidHeartException.class)
                    .hasMessage("마음을 보낼 수 없습니다.");
        }

        @DisplayName("정상적인 호출이면 성공한다.")
        @Test
        void send() {
            Member huni = new Member(HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, IMAGE_URL);
            Member skrr = new Member(SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, IMAGE_URL);
            Heart heart = Heart.start(huni.getId(), skrr.getId());
            Heart reply = Heart.firstReply(skrr.getId(), huni.getId(), heart);
            heart.send(reply);

            assertAll(
                    () -> assertThat(heart.isFinal()).isTrue(),
                    () -> assertThat(reply.isFinal()).isFalse(),
                    () -> assertThat(heart.getCount()).isEqualTo(2),
                    () -> assertThat(reply.getCount()).isEqualTo(1)
            );
        }
    }
}

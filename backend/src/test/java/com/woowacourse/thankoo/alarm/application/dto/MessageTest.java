package com.woowacourse.thankoo.alarm.application.dto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import com.woowacourse.thankoo.alarm.application.MessageFormStrategy;
import com.woowacourse.thankoo.alarm.domain.Alarm;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@DisplayName("Message 는 ")
class MessageTest {

    private MessageFormStrategy messageFormStrategy;

    @BeforeEach
    void setUp() {
        messageFormStrategy = Mockito.mock(MessageFormStrategy.class);
    }

    @DisplayName("Message를 생성한다")
    @Test
    void of() {
        Message expected = new Message("커피 쿠폰이 도착했어요.",
                "www.thankoo.com",
                List.of("hoho@email.com", "huni@email.com"),
                List.of("보내는이: 코치", "제목: 코치가 주는 쿠폰", "커피"));
        given(messageFormStrategy.createFormat(any(Alarm.class))).willReturn(expected);
        Alarm alarm = mock(Alarm.class);

        Message message = Message.of(alarm, messageFormStrategy);

        assertAll(
                () -> assertThat(message.getTitle()).isEqualTo("커피 쿠폰이 도착했어요."),
                () -> assertThat(message.getEmails()).isEqualTo(List.of("hoho@email.com", "huni@email.com")),
                () -> assertThat(message.getContents()).isEqualTo(List.of("보내는이: 코치", "제목: 코치가 주는 쿠폰", "커피")),
                () -> assertThat(message.getTitleLink()).isEqualTo("www.thankoo.com")
        );
    }

    @DisplayName("Message를 빌더로 생성한다")
    @Test
    void builder() {
        Message expected = Message.builder()
                .title("커피 쿠폰이 도착했어요.")
                .titleLink("www.thankoo.com")
                .email(List.of("hoho@email.com", "huni@email.com"))
                .content("보내는이: 코치")
                .content("제목: 코치가 주는 쿠폰")
                .content("커피")
                .build();

        given(messageFormStrategy.createFormat(any(Alarm.class))).willReturn(expected);
        Alarm alarm = mock(Alarm.class);

        Message message = Message.of(alarm, messageFormStrategy);

        assertAll(
                () -> assertThat(message.getTitle()).isEqualTo("커피 쿠폰이 도착했어요."),
                () -> assertThat(message.getEmails()).isEqualTo(List.of("hoho@email.com", "huni@email.com")),
                () -> assertThat(message.getContents()).isEqualTo(List.of("보내는이: 코치", "제목: 코치가 주는 쿠폰", "커피")),
                () -> assertThat(message.getTitleLink()).isEqualTo("www.thankoo.com")
        );
    }
}

package com.woowacourse.thankoo.alarm.application.dto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.woowacourse.thankoo.alarm.application.MessageFormStrategy;
import com.woowacourse.thankoo.alarm.domain.Alarm;
import com.woowacourse.thankoo.common.domain.AlarmSpecification;
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
        Message expected = new Message("title",
                "link",
                List.of("hoho@email.com", "huni@email.com"),
                List.of("sender email", "title: title", "type: coffee"));
        given(messageFormStrategy.createFormat(any(Alarm.class)))
                .willReturn(expected);

        Alarm alarm = Alarm.create(new AlarmSpecification(AlarmSpecification.COUPON_SENT_COFFEE,
                List.of(2L, 3L),
                List.of(String.valueOf(1L), "title", "coffee"))
        );
        Message message = Message.of(alarm, messageFormStrategy);

        assertThat(message).isEqualTo(expected);
    }
}

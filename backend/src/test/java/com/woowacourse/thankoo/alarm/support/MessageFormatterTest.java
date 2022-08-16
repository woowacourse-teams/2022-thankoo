package com.woowacourse.thankoo.alarm.support;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("MessageFormatter 는 ")
class MessageFormatterTest {

    @DisplayName("Message를 Format한다.")
    @ParameterizedTest
    @MethodSource(value = "provideMessagePatterns")
    void format(String messagePattern, Object[] args, String expected) {
        String message = MessageFormatter.format(messagePattern, args);
        assertThat(message).isEqualTo(expected);
    }

    private static Stream<Arguments> provideMessagePatterns() {
        return Stream.of(
                Arguments.of("\\{} = {}", new Object[]{"huni"}, "{} = huni"),
                Arguments.of("\\\\{} = {}", new Object[]{"huni", "huni"}, "\\huni = huni"),
                Arguments.of("쿠폰이 도착했어요.\n땡쿠 알림봇\n땡쿠\n보내는 이 : {}\n쿠폰 타입 : {}\n제목 : {}"
                        , new Object[]{"후니", "커피", "후니가 주는 커피 쿠폰"}
                        , "쿠폰이 도착했어요.\n땡쿠 알림봇\n땡쿠\n보내는 이 : 후니\n쿠폰 타입 : 커피\n제목 : 후니가 주는 커피 쿠폰")
        );
    }
}

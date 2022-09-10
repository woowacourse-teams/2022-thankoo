package com.woowacourse.thankoo.alarm.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.thankoo.alarm.application.strategy.coupon.CouponCoffeeMessageFormStrategy;
import com.woowacourse.thankoo.alarm.application.strategy.coupon.CouponMealMessageFormStrategy;
import com.woowacourse.thankoo.alarm.application.strategy.reservation.ReservationCanceledMessageFormStrategy;
import com.woowacourse.thankoo.alarm.application.strategy.reservation.ReservationRepliedMessageFormStrategy;
import com.woowacourse.thankoo.alarm.application.strategy.reservation.ReservationSentMessageFormStrategy;
import com.woowacourse.thankoo.alarm.domain.AlarmType;
import com.woowacourse.thankoo.common.annotations.ApplicationTest;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("MessageFormStrategyFactory 는 ")
@ApplicationTest
class MessageFormStrategyFactoryTest {

    @Autowired
    private MessageFormStrategyFactory messageFormStrategyFactory;

    @DisplayName("쿠폰 전송 시 메시지 폼 전략을 가져온다.")
    @ParameterizedTest
    @MethodSource("provideEnumForStrategy")
    void getCouponMessageFormStrategy(final AlarmType alarmType, final Class<MessageFormStrategy> clz) {
        MessageFormStrategy strategy = messageFormStrategyFactory.getStrategy(alarmType);
        assertThat(strategy).isInstanceOf(clz);
    }

    private static Stream<Arguments> provideEnumForStrategy() {
        return Stream.of(
                Arguments.of(AlarmType.COUPON_SENT_COFFEE, CouponCoffeeMessageFormStrategy.class),
                Arguments.of(AlarmType.COUPON_SENT_MEAL, CouponMealMessageFormStrategy.class),
                Arguments.of(AlarmType.RESERVATION_SENT, ReservationSentMessageFormStrategy.class),
                Arguments.of(AlarmType.RESERVATION_REPLIED, ReservationRepliedMessageFormStrategy.class),
                Arguments.of(AlarmType.RESERVATION_CANCELED, ReservationCanceledMessageFormStrategy.class)
        );
    }
}

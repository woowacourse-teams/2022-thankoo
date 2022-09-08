package com.woowacourse.thankoo.alarm.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.thankoo.alarm.application.startegy.CouponMessageFormStrategy;
import com.woowacourse.thankoo.alarm.domain.AlarmType;
import com.woowacourse.thankoo.common.annotations.ApplicationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("MessageFormStrategyFactory 는 ")
@ApplicationTest
class MessageFormStrategyFactoryTest {

    @Autowired
    private MessageFormStrategyFactory messageFormStrategyFactory;

    @DisplayName("쿠폰 전송 시 메시지 폼 전략을 가져온다.")
    @Test
    void getCouponMessageFormStrategy() {
        MessageFormStrategy strategy = messageFormStrategyFactory.getStrategy(AlarmType.COUPON_SENT);
        assertThat(strategy).isInstanceOf(CouponMessageFormStrategy.class);
    }
}

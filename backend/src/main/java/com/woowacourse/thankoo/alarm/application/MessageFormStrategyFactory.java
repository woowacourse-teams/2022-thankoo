package com.woowacourse.thankoo.alarm.application;

import com.woowacourse.thankoo.alarm.domain.AlarmType;
import com.woowacourse.thankoo.alarm.exception.StrategyNotSupportedException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class MessageFormStrategyFactory {

    private Map<AlarmType, MessageFormStrategy> strategies;

    public MessageFormStrategyFactory(final Set<MessageFormStrategy> strategies) {
        createStrategy(strategies);
    }

    public MessageFormStrategy getStrategy(final AlarmType alarmType) {
        if (!strategies.containsKey(alarmType)) {
            throw new StrategyNotSupportedException();
        }
        return strategies.get(alarmType);
    }

    private void createStrategy(Set<MessageFormStrategy> strategySet) {
        strategies = new HashMap<>();
        strategySet.forEach(
                strategy -> strategies.put(strategy.getAlarmType(), strategy));
    }
}

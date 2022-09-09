package com.woowacourse.thankoo.alarm.application;

import static java.util.stream.Collectors.toMap;

import com.woowacourse.thankoo.alarm.domain.AlarmType;
import com.woowacourse.thankoo.alarm.exception.StrategyNotSupportedException;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class MessageFormStrategyFactory {

    private Map<AlarmType, MessageFormStrategy> strategies;

    public MessageFormStrategyFactory(final Set<MessageFormStrategy> strategies) {
        initializeStrategies(strategies);
    }

    public MessageFormStrategy getStrategy(final AlarmType alarmType) {
        if (!strategies.containsKey(alarmType)) {
            throw new StrategyNotSupportedException();
        }
        return strategies.get(alarmType);
    }

    private void initializeStrategies(final Set<MessageFormStrategy> strategies) {
        this.strategies = strategies.stream()
                .collect(toMap(MessageFormStrategy::getAlarmType, strategy -> strategy));
    }
}

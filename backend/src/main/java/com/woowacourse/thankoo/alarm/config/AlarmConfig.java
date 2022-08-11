package com.woowacourse.thankoo.alarm.config;

import com.woowacourse.thankoo.alarm.AlarmSender;
import com.woowacourse.thankoo.alarm.infrastructure.SlackAlarmSender;
import com.woowacourse.thankoo.alarm.domain.InMemorySlackUserRepository;
import com.woowacourse.thankoo.alarm.domain.SlackUserMapper;
import com.woowacourse.thankoo.alarm.infrastructure.SlackClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;

@Profile("prod")
@Configuration
public class AlarmConfig {

    private final String token;

    public AlarmConfig(@Value("${slack.token}") final String token) {
        this.token = token;
    }

    @Bean
    public SlackClient slackClient() {
        return new SlackClient(token);
    }

    @DependsOn("slackClient")
    @Bean
    public SlackUserMapper slackUserMapper() {
        return new SlackUserMapper(slackClient());
    }

    @DependsOn("slackUserMapper")
    @Bean
    public InMemorySlackUserRepository inMemorySlackUserRepository() {
        return new InMemorySlackUserRepository(slackUserMapper().getSlackUsers(), slackClient());
    }

    @DependsOn({"slackMessageClient", "inMemorySlackUserRepository"})
    @Bean
    public AlarmSender alarmSender() {
        return new SlackAlarmSender(slackClient(), inMemorySlackUserRepository());
    }
}

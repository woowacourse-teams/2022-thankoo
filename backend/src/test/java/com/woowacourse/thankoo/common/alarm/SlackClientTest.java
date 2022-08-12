package com.woowacourse.thankoo.common.alarm;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SlackClientTest {

    @Autowired
    private SlackClient slackClient;

    @Test
    void test() {
        slackClient.sendAlarm("hyeonho9995@gmail.com", "hello");
    }
}
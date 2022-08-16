package com.woowacourse.thankoo.alarm.support;

import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_EMAIL;

import com.woowacourse.thankoo.alarm.support.Message.Builder;
import java.util.List;
import org.junit.jupiter.api.Test;

class MessageTest {

    @Test
    void test() {
        Message message = new Builder()
                .email(List.of(HOHO_EMAIL))
                .build();

        System.out.println(message.getEmails());
    }
}
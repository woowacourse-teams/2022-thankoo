<<<<<<< HEAD
package com.woowacourse.thankoo.heart.application.dto;

import com.woowacourse.thankoo.alarm.support.Message;
import java.util.Collections;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HeartMessage {

    private static final String TITLE = "누군가 마음을 보냈어요 ❤️";
    private static final String TITLE_LINK = "https://thankoo.co.kr/hearts";

    public static Message of(final String emails) {
        return Message.builder()
                .email(List.of(emails))
                .title(TITLE)
                .titleLink(TITLE_LINK)
                .contents(Collections.emptyList())
                .build();
    }
}
=======
package com.woowacourse.thankoo.heart.application.dto;

import com.woowacourse.thankoo.alarm.application.dto.Message;
import java.util.Collections;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HeartMessage {

    private static final String TITLE = "누군가 마음을 보냈어요 ❤️";
    private static final String TITLE_LINK = "https://thankoo.co.kr/hearts";

    public static Message of(final String emails) {
        return Message.builder()
                .email(List.of(emails))
                .title(TITLE)
                .titleLink(TITLE_LINK)
                .contents(Collections.emptyList())
                .build();
    }
}
>>>>>>> daba63a0244b6bacd428d6c3df98da76456f414a

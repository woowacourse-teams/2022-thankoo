package com.woowacourse.thankoo.admin.serial.presentation;

import com.woowacourse.thankoo.admin.serial.domain.CodeCreator;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.stereotype.Component;

@Component
public class RandomStringCodeCreator implements CodeCreator {

    private static final int CODE_LENGTH = 8;

    private static final Random RANDOM = ThreadLocalRandom.current();

    private static final int START_INDEX = 48;
    private static final int END_INDEX = 122;

    @Override
    public String create() {
        return RANDOM.ints(START_INDEX, END_INDEX + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(CODE_LENGTH)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}

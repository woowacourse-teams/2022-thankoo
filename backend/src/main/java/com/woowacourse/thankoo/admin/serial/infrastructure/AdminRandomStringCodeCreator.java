package com.woowacourse.thankoo.admin.serial.infrastructure;

import com.woowacourse.thankoo.admin.serial.domain.AdminCodeCreator;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.stereotype.Component;

@Component
public class AdminRandomStringCodeCreator implements AdminCodeCreator {

    private static final Random RANDOM = ThreadLocalRandom.current();

    private static final int CODE_LENGTH = 8;
    private static final int START_INDEX = 48;
    private static final int END_INDEX = 122;

    @Override
    public String create() {
        return RANDOM.ints(START_INDEX, END_INDEX + 1)
                .filter(this::isAlphabetOrNumber)
                .limit(CODE_LENGTH)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    private boolean isAlphabetOrNumber(final int i) {
        return (i <= 57 || i >= 65) && (i <= 90 || i >= 97);
    }
}

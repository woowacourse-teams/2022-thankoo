package com.woowacourse.thankoo.common.util;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RandomUtils {

    private static final Random RANDOM = ThreadLocalRandom.current();

    private static final int START_INDEX = 48;                          // '0'
    private static final int END_INDEX = 122;                           // 'z'

    private RandomUtils() {
    }

    public static String nextString(final int endExclusive) {
        return RANDOM.ints(START_INDEX, END_INDEX + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(endExclusive)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}

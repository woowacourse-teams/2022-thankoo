package com.woowacourse.thankoo.common.logging;

public class MaskingUtil {

    public static final String MASKING = "*******";

    private MaskingUtil() {
    }

    public static String mask(final String word, final int substringRange) {
        return word.substring(0, substringRange) + MASKING;
    }
}

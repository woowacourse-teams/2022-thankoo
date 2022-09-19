package com.woowacourse.thankoo.serial.domain;

import com.woowacourse.thankoo.common.util.RandomStringCreator;
import com.woowacourse.thankoo.common.util.RandomUtils;

public class SerialCodeCreator implements RandomStringCreator {

    @Override
    public String create(final int endExclusive) {
        return RandomUtils.nextString(endExclusive);
    }
}

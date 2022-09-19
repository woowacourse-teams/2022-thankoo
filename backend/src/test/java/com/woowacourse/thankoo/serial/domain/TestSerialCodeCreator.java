package com.woowacourse.thankoo.serial.domain;

import static com.woowacourse.thankoo.common.fixtures.SerialFixture.SERIAL_1;

import com.woowacourse.thankoo.common.util.RandomStringCreator;

public class TestSerialCodeCreator implements RandomStringCreator {

    @Override
    public String create(final int endExclusive) {
        return SERIAL_1;
    }
}

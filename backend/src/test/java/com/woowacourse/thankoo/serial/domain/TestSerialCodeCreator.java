package com.woowacourse.thankoo.serial.domain;

import static com.woowacourse.thankoo.common.fixtures.SerialFixture.SERIAL_1;

public class TestSerialCodeCreator implements CodeCreator {

    @Override
    public SerialCode create() {
        return new SerialCode(SERIAL_1);
    }
}

package com.woowacourse.thankoo.admin.serial.domain;

import static com.woowacourse.thankoo.common.fixtures.SerialFixture.SERIAL_1;

public class TestSerialAdminCodeCreator implements AdminCodeCreator {

    @Override
    public String create() {
        return SERIAL_1;
    }
}

package com.woowacourse.thankoo.serial.domain;

import com.woowacourse.thankoo.common.util.CodeCreator;
import com.woowacourse.thankoo.common.util.RandomUtils;

public class SerialCodeCreator implements CodeCreator {

    private static final int CODE_LENGTH = 8;

    @Override
    public SerialCode create() {
        return new SerialCode(RandomUtils.nextString(CODE_LENGTH));
    }
}

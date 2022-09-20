package com.woowacourse.thankoo.serial.infrastructure;

import com.woowacourse.thankoo.common.util.RandomUtils;
import com.woowacourse.thankoo.serial.domain.CodeCreator;
import com.woowacourse.thankoo.serial.domain.SerialCode;

public class SerialCodeCreator implements CodeCreator {

    private static final int CODE_LENGTH = 8;

    @Override
    public SerialCode create() {
        return new SerialCode(RandomUtils.nextString(CODE_LENGTH));
    }
}

package com.woowacourse.thankoo.admin.serial.infrastructure;

import com.woowacourse.thankoo.admin.serial.domain.AdminCodeCreator;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class AdminRandomStringCodeCreator implements AdminCodeCreator {

    private static final int CODE_LENGTH = 8;

    @Override
    public String create() {
        return UUID.randomUUID()
                .toString()
                .substring(0, CODE_LENGTH);
    }
}

package com.woowacourse.thankoo.organization.infrastructure;

import com.woowacourse.thankoo.organization.domain.CodeGenerator;
import java.util.UUID;

public class FakeCodeGenerator implements CodeGenerator {

    @Override
    public String create(final int length) {
        return UUID.randomUUID()
                .toString()
                .substring(0, length - 1);
    }
}

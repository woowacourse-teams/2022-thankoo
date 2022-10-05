package com.woowacourse.thankoo.organization.infrastructure;

import com.woowacourse.thankoo.organization.domain.CodeGenerator;
import java.util.UUID;

public class OrganizationCodeGenerator implements CodeGenerator {

    private static final int START_INDEX = 0;

    @Override
    public String create(final int length) {
        return UUID.randomUUID()
                .toString()
                .substring(START_INDEX, length);
    }
}

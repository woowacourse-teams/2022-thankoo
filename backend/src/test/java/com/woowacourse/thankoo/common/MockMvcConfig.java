package com.woowacourse.thankoo.common;

import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcBuilderCustomizer;
import org.springframework.test.web.servlet.setup.ConfigurableMockMvcBuilder;

public class MockMvcConfig implements MockMvcBuilderCustomizer {

    @Override
    public void customize(final ConfigurableMockMvcBuilder<?> builder) {
        builder.alwaysDo(result -> result.getResponse().setCharacterEncoding("UTF-8"));
    }
}

package com.woowacourse.thankoo.admin.serial.presentation;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.thankoo.admin.serial.domain.CodeCreator;
import com.woowacourse.thankoo.admin.serial.infrastructure.RandomStringCodeCreator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RandomStringCodeCreatorTest {

    @DisplayName("랜덤 스트링을 생성한다.")
    @Test
    void nextString() {
        CodeCreator codeCreator = new RandomStringCodeCreator();

        assertThat(codeCreator.create()).hasSize(8);
    }
}

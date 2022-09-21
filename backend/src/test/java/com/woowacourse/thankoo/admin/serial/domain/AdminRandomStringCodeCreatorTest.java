package com.woowacourse.thankoo.admin.serial.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.thankoo.admin.serial.infrastructure.AdminRandomStringCodeCreator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("AdminRandomStringCodeCreator 는 ")
class AdminRandomStringCodeCreatorTest {

    @DisplayName("랜덤 스트링을 생성한다.")
    @Test
    void nextString() {
        AdminCodeCreator adminCodeCreator = new AdminRandomStringCodeCreator();

        assertThat(adminCodeCreator.create()).hasSize(8);
    }
}

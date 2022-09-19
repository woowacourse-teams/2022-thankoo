package com.woowacourse.thankoo.admin.serial.util;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.thankoo.common.util.RandomUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("RandomUtils 는 ")
class RandomUtilsTest {

    @DisplayName("자릿수에 맞는 랜덤 스트링을 생성한다.")
    @ParameterizedTest
    @ValueSource(ints = {5, 6, 7})
    void nextString(int length) {
        String result = RandomUtils.nextString(length);

        assertThat(result.length()).isEqualTo(length);
    }
}

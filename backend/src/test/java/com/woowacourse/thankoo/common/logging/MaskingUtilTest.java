package com.woowacourse.thankoo.common.logging;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("MaskingUtil 은 ")
class MaskingUtilTest {

    @Test
    @DisplayName("지정된 범위 이후의 문자를 마스킹한다.")
    void mask() {
        String maskedWord = MaskingUtil.mask("eyJhb.eyJzdWIiOiIxMjM0NTY3ODk.SflKxwRJSMe", 3);

        assertThat(maskedWord).isEqualTo("eyJ*******");
    }
}

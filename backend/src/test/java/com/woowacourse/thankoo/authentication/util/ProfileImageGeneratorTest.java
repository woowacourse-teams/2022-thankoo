package com.woowacourse.thankoo.authentication.util;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.thankoo.common.util.ProfileImageGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("RandomProfileImageGenerator 는 ")
class ProfileImageGeneratorTest {

    @DisplayName("프로필 이미지 url을 무작위로 가져온다.")
    @Test
    void getRandomImage() {
        String imageUrl = ProfileImageGenerator.getRandomImage();
        assertThat(imageUrl).isNotNull();
    }
}

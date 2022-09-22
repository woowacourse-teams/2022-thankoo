package com.woowacourse.thankoo.common.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@DisplayName("RandomProfileImageGenerator 는 ")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ProfileImageGeneratorTest {

    @Autowired
    ProfileImageGenerator profileImageGenerator;

    @DisplayName("프로필 이미지 url을 무작위로 가져온다.")
    @Test
    void getRandomImage() {
        String imageUrl = profileImageGenerator.getRandomImage();
        assertThat(imageUrl).isNotNull();
    }
}

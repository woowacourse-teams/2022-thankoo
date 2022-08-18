package com.woowacourse.thankoo.authentication.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.thankoo.common.exception.BadRequestException;
import com.woowacourse.thankoo.common.util.ProfileImageGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("RandomProfileImageGenerator 는 ")
class ProfileImageGeneratorTest {

    @DisplayName("프로필 이미지 url을 무작위로 가져온다.")
    @Test
    void getRandomImage() {
        String imageUrl = ProfileImageGenerator.getRandomImage();
        assertThat(imageUrl).isNotNull();
    }

    @DisplayName("프로필 이미지 요청 경로를 생성할 때 ")
    @Nested
    class ImageUrlCreationTest {

        @DisplayName("프로필 이미지가 존재하면 요청 경로를 생성한다.")
        @Test
        void getImageUrl() {
            String imageUrl = ProfileImageGenerator.getImageUrl("user_skull.svg");
            assertThat(imageUrl).isEqualTo("/profile-image/user_skull.svg");
        }

        @DisplayName("프로필 이미지가 존재하지 않으면 예외가 발생한다.")
        @Test
        void getImageUrlWithInvalidImageName() {
            assertThatThrownBy(
                () -> ProfileImageGenerator.getImageUrl("invalidImage")
            )
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("올바르지 않은 프로필 이미지입니다.");
        }
    }
}

package com.woowacourse.thankoo.authentication.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.thankoo.common.exception.BadRequestException;
import com.woowacourse.thankoo.common.util.ProfileImageGenerator;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

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

    @Test
    void t() throws IOException {
        Resource[] resources = (new PathMatchingResourcePatternResolver()).getResources("classpath:static/profile-image/*.svg");
        List<String> collect = Stream.of(resources)
                .map(Resource::getFilename)
                .collect(Collectors.toList());
        System.out.println(collect);

    }

    @DisplayName("프로필 이미지 요청 경로를 생성할 때 ")
    @Nested
    class ImageUrlCreationTest {

        @DisplayName("프로필 이미지가 존재하면 요청 경로를 생성한다.")
        @Test
        void getImageUrl() {
            String imageUrl = profileImageGenerator.getImageUrl("user_skull.svg");
            assertThat(imageUrl).isEqualTo("/profile-image/user_skull.svg");
        }

        @DisplayName("프로필 이미지가 존재하지 않으면 예외가 발생한다.")
        @Test
        void getImageUrlWithInvalidImageName() {
            assertThatThrownBy(
                    () -> profileImageGenerator.getImageUrl("invalidImage")
            )
                    .isInstanceOf(BadRequestException.class)
                    .hasMessageContaining("올바르지 않은 프로필 이미지입니다.");
        }
    }
}

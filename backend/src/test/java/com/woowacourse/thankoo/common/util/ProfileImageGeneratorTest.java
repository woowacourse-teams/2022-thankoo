package com.woowacourse.thankoo.common.util;

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
}

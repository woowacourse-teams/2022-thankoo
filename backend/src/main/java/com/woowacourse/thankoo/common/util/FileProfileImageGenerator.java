package com.woowacourse.thankoo.common.util;

import com.woowacourse.thankoo.common.exception.ErrorType;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

@Component
public class FileProfileImageGenerator implements ProfileImageGenerator {

    private static final int RANDOM_MIN_RANGE = 0;
    private final String imageUrlPath;
    private final List<String> profileImages;
    private final Random random;

    private FileProfileImageGenerator(@Value("${profile-image.image-url-path}") final String imageUrlPath,
                                      @Value("${profile-image.image-path}") final String imagePath) {
        this.random = ThreadLocalRandom.current();
        this.imageUrlPath = imageUrlPath;
        try {
            Resource[] resources = (new PathMatchingResourcePatternResolver())
                    .getResources(imagePath);
            profileImages = Arrays.stream(resources)
                    .map(Resource::getFilename)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new IllegalArgumentException(ErrorType.INVALID_PATH.getMessage());
        }
    }

    @Override
    public String getRandomImage() {
        int imageIndex = getRandomNumber(profileImages.size());
        return imageUrlPath + profileImages.get(imageIndex);
    }

    private int getRandomNumber(final int maxExcludeRange) {
        return random.ints(RANDOM_MIN_RANGE, maxExcludeRange)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public List<String> getImageUrls() {
        return profileImages.stream()
                .map(imageName -> imageUrlPath + imageName)
                .collect(Collectors.toList());
    }
}

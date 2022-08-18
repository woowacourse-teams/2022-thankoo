package com.woowacourse.thankoo.common.util;

import com.woowacourse.thankoo.common.exception.BadRequestException;
import com.woowacourse.thankoo.common.exception.ErrorType;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

@Component
public class FileProfileImageGenerator implements ProfileImageGenerator {

    private static final String IMAGE_URL_PATH = "/profile-image/";
    private static final String IMAGE_PATH = "classpath:static/profile-image";
    private static final int RANDOM_MIN_RANGE = 0;
    private final List<String> profileImages;
    private final Random random;

    private FileProfileImageGenerator() {
        random = new Random();
        try {
            File[] files = ResourceUtils.getFile(IMAGE_PATH)
                    .listFiles();
            profileImages = Arrays.stream(files)
                    .map(File::getName)
                    .collect(Collectors.toList());
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException(ErrorType.INVALID_PATH.getMessage());
        }
    }

    @Override
    public String getRandomImage() {
        int imageIndex = getRandomNumber(profileImages.size());
        return IMAGE_URL_PATH + profileImages.get(imageIndex);
    }

    private int getRandomNumber(final int maxExcludeRange) {
        return random.ints(RANDOM_MIN_RANGE, maxExcludeRange)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public String getImageUrl(final String imageName) {
        validateImageName(imageName);
        return IMAGE_URL_PATH + imageName;
    }

    private void validateImageName(final String imageName) {
        if (!hasSameImage(imageName)) {
            throw new BadRequestException(ErrorType.INVALID_MEMBER_PROFILE_IMAGE);
        }
    }

    private boolean hasSameImage(final String imageName) {
        return profileImages.stream()
                .anyMatch(imageName::equals);
    }

    @Override
    public List<String> getImageUrls() {
        return profileImages.stream()
                .map(imageName -> IMAGE_URL_PATH + imageName)
                .collect(Collectors.toList());
    }
}

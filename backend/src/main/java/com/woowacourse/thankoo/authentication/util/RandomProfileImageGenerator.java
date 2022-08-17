package com.woowacourse.thankoo.authentication.util;

import com.woowacourse.thankoo.common.exception.ErrorType;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.util.ResourceUtils;

public class RandomProfileImageGenerator {

    private static final String IMAGE_PATH = "classpath:static/profile-image";
    private static final int FIRST_IMAGE = 0;
    private static List<String> profileImages;

    static {
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

    public static String getRandomImage() {
        Collections.shuffle(profileImages);
        return profileImages.get(FIRST_IMAGE);
    }
}

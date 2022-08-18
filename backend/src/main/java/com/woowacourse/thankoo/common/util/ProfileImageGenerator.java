package com.woowacourse.thankoo.common.util;

import java.util.List;

//@Component
public interface ProfileImageGenerator {

    String getRandomImage();

    String getImageUrl(String imageName);

    List<String> getImageUrls();
}

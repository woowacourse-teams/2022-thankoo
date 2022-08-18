package com.woowacourse.thankoo.common.fake;

import com.woowacourse.thankoo.common.exception.BadRequestException;
import com.woowacourse.thankoo.common.exception.ErrorType;
import com.woowacourse.thankoo.common.util.ProfileImageGenerator;
import java.util.List;

public class FakeProfileImageGenerator implements ProfileImageGenerator {

    @Override
    public String getRandomImage() {
        return null;
    }

    @Override
    public String getImageUrl(final String imageName) {
        if (imageName.equals("unknownImage")) {
            throw new BadRequestException(ErrorType.INVALID_MEMBER_PROFILE_IMAGE);
        }
        return imageName;
    }

    @Override
    public List<String> getImageUrls() {
        return null;
    }
}

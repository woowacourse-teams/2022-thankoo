package com.woowacourse.thankoo.common.fixtures;

import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.IMAGE_URL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_SOCIAL_ID;

import com.woowacourse.thankoo.authentication.infrastructure.dto.GoogleProfileResponse;
import java.util.HashMap;
import java.util.Map;

public class OAuthFixture {

    public static final String CODE_HOHO = "code_hoho";
    public static final String CODE_SKRR = "code_skrr";
    public static final String CODE_LALA = "code_lala";
    public static final String CODE_HUNI = "code_huni";

    public static final String TOKEN_HOHO = "token_hoho";
    public static final String TOKEN_SKRR = "token_skrr";
    public static final String TOKEN_LALA = "token_lala";
    public static final String TOKEN_HUNI = "token_huni";

    public static final GoogleProfileResponse PROFILE_HOHO = new GoogleProfileResponse(HOHO_SOCIAL_ID, HOHO_EMAIL,
            IMAGE_URL);
    public static final GoogleProfileResponse PROFILE_SKRR = new GoogleProfileResponse(SKRR_SOCIAL_ID, SKRR_EMAIL,
            IMAGE_URL);
    public static final GoogleProfileResponse PROFILE_LALA = new GoogleProfileResponse(LALA_SOCIAL_ID, LALA_EMAIL,
            IMAGE_URL);
    public static final GoogleProfileResponse PROFILE_HUNI = new GoogleProfileResponse(HUNI_SOCIAL_ID, HUNI_EMAIL,
            IMAGE_URL);

    public static final Map<String, String> CACHED_OAUTH_TOKEN;
    public static final Map<String, GoogleProfileResponse> CACHED_OAUTH_PROFILE;

    static {
        CACHED_OAUTH_TOKEN = new HashMap<>();
        CACHED_OAUTH_TOKEN.put(CODE_HOHO, TOKEN_HOHO);
        CACHED_OAUTH_TOKEN.put(CODE_SKRR, TOKEN_SKRR);
        CACHED_OAUTH_TOKEN.put(CODE_LALA, TOKEN_LALA);
        CACHED_OAUTH_TOKEN.put(CODE_HUNI, TOKEN_HUNI);

        CACHED_OAUTH_PROFILE = new HashMap<>();
        CACHED_OAUTH_PROFILE.put(TOKEN_HOHO, PROFILE_HOHO);
        CACHED_OAUTH_PROFILE.put(TOKEN_SKRR, PROFILE_SKRR);
        CACHED_OAUTH_PROFILE.put(TOKEN_LALA, PROFILE_LALA);
        CACHED_OAUTH_PROFILE.put(TOKEN_HUNI, PROFILE_HUNI);
    }
}

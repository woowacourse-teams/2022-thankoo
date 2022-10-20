package com.woowacourse.thankoo.common.fixtures;

import java.util.HashMap;
import java.util.Map;

public class OAuthFixture {

    public static final String CODE_HOHO = "code_hoho";
    public static final String CODE_SKRR = "code_skrr";
    public static final String CODE_LALA = "code_lala";
    public static final String CODE_HUNI = "code_huni";

    public static final String HOHO_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxIiwiZW1haWwiOiJob2hvQGVtYWlsLmNvbSIsInBpY3R1cmUiOiJpbWFnZS5jb20ifQ.3lOADGvl15qYXZS45MYCZ-h09qXLCNHtYtgijgxo9qA";
    public static final String SKRR_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIyIiwiZW1haWwiOiJza3JyQGVtYWlsLmNvbSIsInBpY3R1cmUiOiJpbWFnZS5jb20ifQ.qe6YgIqP8sZUw1GJ82r5NoRoPRab_kSxRex257mzVxI";
    public static final String LALA_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIzIiwiZW1haWwiOiJsYWxhQGVtYWlsLmNvbSIsInBpY3R1cmUiOiJpbWFnZS5jb20ifQ.5nbOQp4BdgFVY_ri8Fx9K0E4b83YWE_1St7XsxlimqU";
    public static final String HUNI_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI0IiwiZW1haWwiOiJodW5pQGVtYWlsLmNvbSIsInBpY3R1cmUiOiJpbWFnZS5jb20ifQ.vqiYqkY6wpc_v1SeUkRFKl68oM_UaHWTR2xuM-k3r_E";

    public static final Map<String, String> CACHED_OAUTH_PROFILE;

    static {
        CACHED_OAUTH_PROFILE = new HashMap<>();
        CACHED_OAUTH_PROFILE.put(CODE_HOHO, HOHO_TOKEN);
        CACHED_OAUTH_PROFILE.put(CODE_SKRR, SKRR_TOKEN);
        CACHED_OAUTH_PROFILE.put(CODE_LALA, LALA_TOKEN);
        CACHED_OAUTH_PROFILE.put(CODE_HUNI, HUNI_TOKEN);
    }

    private OAuthFixture() {
    }
}

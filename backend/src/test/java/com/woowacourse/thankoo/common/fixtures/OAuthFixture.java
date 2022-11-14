package com.woowacourse.thankoo.common.fixtures;

import java.util.HashMap;
import java.util.Map;

public class OAuthFixture {

    public static final String CODE_HOHO = "code_hoho";
    public static final String CODE_SKRR = "code_skrr";
    public static final String CODE_LALA = "code_lala";
    public static final String CODE_HUNI = "code_huni";

    public static final String HOHO_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxIiwiZW1haWwiOiJob2hvQGVtYWlsLmNvbSIsInBpY3R1cmUiOiJpbWFnZS5jb20iLCJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJhdWQiOiJjbGllbnQtaWQifQ.yji8dIytPuAE1GWkPZ20J-vbLiTxbjEQgkvSg2zRYzw";
    public static final String SKRR_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIyIiwiZW1haWwiOiJza3JyQGVtYWlsLmNvbSIsInBpY3R1cmUiOiJpbWFnZS5jb20iLCJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJhdWQiOiJjbGllbnQtaWQifQ.5nVxMWArSsLZAJVLcbYodqK0QlQi1oIa0e57KT5HQBc";
    public static final String LALA_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIzIiwiZW1haWwiOiJsYWxhQGVtYWlsLmNvbSIsInBpY3R1cmUiOiJpbWFnZS5jb20iLCJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJhdWQiOiJjbGllbnQtaWQifQ.RNQd9kWj5rDlVyWCFj2Lffb6yQt15aTs3TX2OeHz5zk";
    public static final String HUNI_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI0IiwiZW1haWwiOiJodW5pQGVtYWlsLmNvbSIsInBpY3R1cmUiOiJpbWFnZS5jb20iLCJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJhdWQiOiJjbGllbnQtaWQifQ.eFtGoJJu6QoWNXilcBTM_UcabGEgkCC92agxzudlTBg";

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

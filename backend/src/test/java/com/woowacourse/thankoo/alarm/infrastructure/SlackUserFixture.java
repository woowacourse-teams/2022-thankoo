package com.woowacourse.thankoo.alarm.infrastructure;

import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_EMAIL;

import java.util.Map;

public class SlackUserFixture {

    public static final String HOHO_USER_TOKEN = "UN1203OFJ12";
    public static final String HUNI_USER_TOKEN = "UN340F3MS14";
    public static final String LALA_USER_TOKEN = "UN5603FBA51";
    public static final String SKRR_USER_TOKEN = "UN7803GJ41D";

    public static final Map<String, String> store = Map.of(
            HOHO_USER_TOKEN, HOHO_EMAIL,
            HUNI_USER_TOKEN, HUNI_EMAIL,
            LALA_USER_TOKEN, LALA_EMAIL,
            SKRR_USER_TOKEN, SKRR_EMAIL);

    public static final String[] HOHO = {HOHO_EMAIL, HOHO_USER_TOKEN};
    public static final String[] HUNI = {HUNI_EMAIL, HUNI_USER_TOKEN};
    public static final String[] LALA = {LALA_EMAIL, LALA_USER_TOKEN};
    public static final String[] SKRR = {SKRR_EMAIL, SKRR_USER_TOKEN};
}

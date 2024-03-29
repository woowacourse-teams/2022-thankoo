package com.woowacourse.thankoo.common.fixtures;

import com.woowacourse.thankoo.organization.domain.Organization;
import com.woowacourse.thankoo.organization.domain.OrganizationValidator;

public class OrganizationFixture {

    public static final String ORGANIZATION_WOOWACOURSE = "우아한테크코스 4기";
    public static final String ORGANIZATION_THANKOO = "땡쿠";
    public static final String ORGANIZATION_TOSS = "토스";

    public static final String ORGANIZATION_WOOWACOURSE_CODE = "WOOWACO1";
    public static final String ORGANIZATION_THANKOO_CODE = "THANKOO1";
    public static final String ORGANIZATION_TOSS_CODE = "TOSSBANK";
    public static final String ORGANIZATION_NO_CODE = "NO_ORG12";

    public static Organization createDefaultOrganization(final OrganizationValidator organizationValidator) {
        return Organization.create(ORGANIZATION_WOOWACOURSE, length -> ORGANIZATION_WOOWACOURSE_CODE, 10,
                organizationValidator);
    }

    public static Organization createThankooOrganization(final OrganizationValidator organizationValidator) {
        return Organization.create(ORGANIZATION_THANKOO, length -> ORGANIZATION_THANKOO_CODE, 50,
                organizationValidator);
    }

    public static Organization createTossOrganization(final OrganizationValidator organizationValidator) {
        return Organization.create(ORGANIZATION_TOSS, length -> ORGANIZATION_TOSS_CODE, 50,
                organizationValidator);
    }

    private OrganizationFixture() {
    }
}

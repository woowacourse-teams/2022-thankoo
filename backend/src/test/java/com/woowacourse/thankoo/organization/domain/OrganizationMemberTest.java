package com.woowacourse.thankoo.organization.domain;

import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_IMAGE_URL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.OrganizationFixture.ORGANIZATION_NAME;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.organization.infrastructure.OrganizationCodeGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("OrganizationMember 는 ")
class OrganizationMemberTest {

    @DisplayName("정상적으로 생성한다.")
    @Test
    void create() {
        Organization organization = Organization.create(ORGANIZATION_NAME, new OrganizationCodeGenerator(), 100);
        assertDoesNotThrow(() -> new OrganizationMember(
                new Member(HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, HUNI_IMAGE_URL),
                organization,
                1,
                true));
    }
}

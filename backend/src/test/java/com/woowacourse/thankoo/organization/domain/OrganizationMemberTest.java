package com.woowacourse.thankoo.organization.domain;

import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_IMAGE_URL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.OrganizationFixture.ORGANIZATION_WOOWACOURSE;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.organization.infrastructure.OrganizationCodeGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@DisplayName("OrganizationMember 는 ")
class OrganizationMemberTest {

    @DisplayName("정상적으로 생성한다.")
    @Test
    void create() {
        OrganizationValidator validator = Mockito.mock(OrganizationValidator.class);
        doNothing().when(validator).validate(any(Organization.class));

        Organization organization = Organization.create(ORGANIZATION_WOOWACOURSE, new OrganizationCodeGenerator(), 100,
                validator);
        assertDoesNotThrow(() -> new OrganizationMember(
                new Member(HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, HUNI_IMAGE_URL),
                organization,
                1,
                true));
    }
}

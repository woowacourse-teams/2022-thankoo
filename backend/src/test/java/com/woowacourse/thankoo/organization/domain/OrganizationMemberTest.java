package com.woowacourse.thankoo.organization.domain;

import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_IMAGE_URL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.OrganizationFixture.ORGANIZATION_WOOWACOURSE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.organization.infrastructure.OrganizationCodeGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@DisplayName("OrganizationMember 는 ")
class OrganizationMemberTest {

    private OrganizationValidator organizationValidator;

    @BeforeEach
    void setUp() {
        organizationValidator = Mockito.mock(OrganizationValidator.class);
        doNothing().when(organizationValidator).validate(any(Organization.class));
    }

    @DisplayName("정상적으로 생성한다.")
    @Test
    void create() {
        Organization organization = Organization.create(ORGANIZATION_WOOWACOURSE, new OrganizationCodeGenerator(), 100,
                organizationValidator);
        assertDoesNotThrow(() -> new OrganizationMember(
                new Member(HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, HUNI_IMAGE_URL),
                organization,
                1,
                true));
    }

    @DisplayName("동일한 멤버이다.")
    @Test
    void isSameMember() {
        Organization organization = Organization.create(ORGANIZATION_WOOWACOURSE, new OrganizationCodeGenerator(), 100,
                organizationValidator);

        Member member = new Member(1L, HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, HUNI_IMAGE_URL);
        OrganizationMember organizationMember = new OrganizationMember(
                member,
                organization,
                1,
                true);

        assertThat(organizationMember.isSameMember(member)).isTrue();
    }
}

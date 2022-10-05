package com.woowacourse.thankoo.organization.domain;

import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_IMAGE_URL;
import static com.woowacourse.thankoo.common.fixtures.OrganizationFixture.ORGANIZATION_WOOWACOURSE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

import com.woowacourse.thankoo.member.domain.Member;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class OrganizationMembersTest {

    private OrganizationValidator organizationValidator;

    @BeforeEach
    void setUp() {
        organizationValidator = Mockito.mock(OrganizationValidator.class);
        doNothing().when(organizationValidator).validate(any(Organization.class));
    }

    @DisplayName("회원 수를 조회한다.")
    @Test
    void size() {
        Organization organization = Organization.create(ORGANIZATION_WOOWACOURSE, length -> "ABCDEFG1", 100,
                organizationValidator);
        Member lala = new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, SKRR_IMAGE_URL);
        Member hoho = new Member(HOHO_NAME, HOHO_EMAIL, HOHO_SOCIAL_ID, SKRR_IMAGE_URL);
        Member huni = new Member(HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, SKRR_IMAGE_URL);
        List<OrganizationMember> organizationMembers = List.of(new OrganizationMember(lala, organization, 1, true),
                new OrganizationMember(hoho, organization, 1, true),
                new OrganizationMember(huni, organization, 1, true));

        assertThat(new OrganizationMembers(organizationMembers).size()).isEqualTo(3);
    }

    @DisplayName("조직에 회원을 추가한다.")
    @Test
    void add() {
        Organization organization = Organization.create(ORGANIZATION_WOOWACOURSE, length -> "ABCDEFG1", 100,
                organizationValidator);
        Member member = new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, SKRR_IMAGE_URL);
        OrganizationMembers organizationMembers = new OrganizationMembers(new ArrayList<>());

        organizationMembers.add(new OrganizationMember(member, organization, 1, true));
        assertThat(organizationMembers.size()).isEqualTo(1);
    }

    @DisplayName("이미 회원이 참여중이다.")
    @Test
    void isMemberExist() {
        Organization organization = Organization.create(ORGANIZATION_WOOWACOURSE, length -> "ABCDEFG1", 100,
                organizationValidator);
        Member member = new Member(1L, LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, SKRR_IMAGE_URL);
        OrganizationMembers organizationMembers = new OrganizationMembers(new ArrayList<>());

        organizationMembers.add(new OrganizationMember(member, organization, 1, true));
        assertThat(organizationMembers.isMemberAlreadyExist(member)).isTrue();
    }
}

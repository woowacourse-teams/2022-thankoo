package com.woowacourse.thankoo.organization.domain;

import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_IMAGE_URL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_IMAGE_URL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.OrganizationFixture.ORGANIZATION_THANKOO;
import static com.woowacourse.thankoo.common.fixtures.OrganizationFixture.ORGANIZATION_THANKOO_CODE;
import static com.woowacourse.thankoo.common.fixtures.OrganizationFixture.ORGANIZATION_WOOWACOURSE;
import static com.woowacourse.thankoo.common.fixtures.OrganizationFixture.ORGANIZATION_WOOWACOURSE_CODE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

import com.woowacourse.thankoo.member.domain.Member;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
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
        Organization organization = Organization.create(ORGANIZATION_WOOWACOURSE,
                length -> ORGANIZATION_WOOWACOURSE_CODE, 100,
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
        Organization organization = Organization.create(ORGANIZATION_WOOWACOURSE,
                length -> ORGANIZATION_WOOWACOURSE_CODE, 100,
                organizationValidator);
        Member member = new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, SKRR_IMAGE_URL);
        OrganizationMembers organizationMembers = new OrganizationMembers(new ArrayList<>());

        organizationMembers.add(new OrganizationMember(member, organization, 1, true));
        assertThat(organizationMembers.size()).isEqualTo(1);
    }

    @DisplayName("이미 조직을 포함한다.")
    @Test
    void containsOrganization() {
        Organization organization = Organization.create(ORGANIZATION_WOOWACOURSE,
                length -> ORGANIZATION_WOOWACOURSE_CODE, 100,
                organizationValidator);
        Member member = new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, SKRR_IMAGE_URL);
        OrganizationMembers organizationMembers = new OrganizationMembers(new ArrayList<>());
        organizationMembers.add(new OrganizationMember(member, organization, 1, true));

        assertThat(organizationMembers.containsOrganization(organization)).isTrue();
    }

    @DisplayName("이전에 접근한 것으로 변경한다.")
    @Test
    void toPreviousAccessed() {
        Organization organization = Organization.create(ORGANIZATION_WOOWACOURSE,
                length -> ORGANIZATION_WOOWACOURSE_CODE, 100,
                organizationValidator);
        Member member = new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, SKRR_IMAGE_URL);
        OrganizationMembers organizationMembers = new OrganizationMembers(new ArrayList<>());
        organizationMembers.add(new OrganizationMember(member, organization, 1, true));
        organizationMembers.toPreviousAccessed();

        assertThat(organizationMembers.getValues().get(0).isLastAccessed()).isFalse();
    }

    @DisplayName("나를 제외한 조직원을 조회한다.")
    @Test
    void getExclude() {
        Organization organization = Organization.create(ORGANIZATION_WOOWACOURSE,
                length -> ORGANIZATION_WOOWACOURSE_CODE, 100,
                organizationValidator);

        Member lala = new Member(1L, LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, LALA_IMAGE_URL);
        Member skrr = new Member(2L, SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, LALA_IMAGE_URL);

        OrganizationMembers organizationMembers = new OrganizationMembers(new ArrayList<>());
        organizationMembers.add(new OrganizationMember(lala, organization, 1, true));
        organizationMembers.add(new OrganizationMember(skrr, organization, 1, true));

        List<OrganizationMember> organizationMembersExclude = organizationMembers.getExclude(skrr);

        assertAll(
                () -> assertThat(organizationMembersExclude).hasSize(1),
                () -> assertThat(organizationMembersExclude.get(0).getMember()).isEqualTo(lala)
        );
    }

    @DisplayName("조직에 회원 존재 여부를 확인할 때")
    @Nested
    class ContainsTest {

        @DisplayName("존재하면 true 를 반환한다.")
        @Test
        void containsMember() {
            Organization organization = Organization.create(ORGANIZATION_WOOWACOURSE,
                    length -> ORGANIZATION_WOOWACOURSE_CODE, 100,
                    organizationValidator);
            Member member = new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, SKRR_IMAGE_URL);
            OrganizationMembers organizationMembers = new OrganizationMembers(new ArrayList<>());
            organizationMembers.add(new OrganizationMember(member, organization, 1, true));

            assertThat(organizationMembers.containsMember(member)).isTrue();
        }

        @DisplayName("존재하지 않으면 false 를 반환한다.")
        @Test
        void notContainsMember() {
            Member member = new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, SKRR_IMAGE_URL);
            OrganizationMembers organizationMembers = new OrganizationMembers(new ArrayList<>());

            assertThat(organizationMembers.containsMember(member)).isFalse();
        }
    }

    @DisplayName("해당 조직을 최근 접근한 조직으로 변경한다.")
    @Test
    void switchLastAccessed() {
        Organization woowacourse = new Organization(1L, new OrganizationName(ORGANIZATION_WOOWACOURSE),
                new OrganizationCode(ORGANIZATION_WOOWACOURSE_CODE), 120, new OrganizationMembers(List.of()));
        Organization thankoo = new Organization(2L, new OrganizationName(ORGANIZATION_THANKOO),
                new OrganizationCode(ORGANIZATION_THANKOO_CODE), 120, new OrganizationMembers(List.of()));
        Organization toss = new Organization(3L, new OrganizationName(ORGANIZATION_THANKOO),
                new OrganizationCode(ORGANIZATION_THANKOO_CODE), 120, new OrganizationMembers(List.of()));

        Member member = new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, SKRR_IMAGE_URL);
        OrganizationMembers organizationMembers = new OrganizationMembers(new ArrayList<>());
        organizationMembers.add(new OrganizationMember(member, woowacourse, 1, true));
        organizationMembers.add(new OrganizationMember(member, thankoo, 2, true));
        organizationMembers.add(new OrganizationMember(member, toss, 3, true));

        organizationMembers.switchLastAccessed(thankoo);

        assertAll(
                () -> assertThat(organizationMembers.getValues().get(0).isLastAccessed()).isFalse(),
                () -> assertThat(organizationMembers.getValues().get(1).isLastAccessed()).isTrue(),
                () -> assertThat(organizationMembers.getValues().get(2).isLastAccessed()).isFalse()
        );
    }
}

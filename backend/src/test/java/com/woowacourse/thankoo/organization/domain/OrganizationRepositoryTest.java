package com.woowacourse.thankoo.organization.domain;

import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_IMAGE_URL;
import static com.woowacourse.thankoo.common.fixtures.OrganizationFixture.ORGANIZATION_WOOWACOURSE;
import static com.woowacourse.thankoo.common.fixtures.OrganizationFixture.ORGANIZATION_WOOWACOURSE_CODE;
import static com.woowacourse.thankoo.common.fixtures.OrganizationFixture.createDefaultOrganization;
import static com.woowacourse.thankoo.common.fixtures.OrganizationFixture.createThankooOrganization;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

import com.woowacourse.thankoo.common.annotations.RepositoryTest;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import com.woowacourse.thankoo.organization.infrastructure.OrganizationCodeGenerator;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("OrganizationRepository 는 ")
@RepositoryTest
class OrganizationRepositoryTest {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private MemberRepository memberRepository;

    private OrganizationValidator organizationValidator;

    @BeforeEach
    void setUp() {
        organizationValidator = Mockito.mock(OrganizationValidator.class);
        doNothing().when(organizationValidator).validate(any(Organization.class));
    }

    @DisplayName("동일한 코드가 있는지 확인할 때 ")
    @Nested
    class ExistsByCodeTest {

        @DisplayName("존재하면 true를 반환한다.")
        @Test
        void exist() {
            CodeGenerator codeGenerator = Mockito.mock(CodeGenerator.class);
            given(codeGenerator.create(8)).willReturn("ABCDEFG1");

            organizationRepository.save(
                    Organization.create(ORGANIZATION_WOOWACOURSE, codeGenerator, 100, organizationValidator));

            assertThat(organizationRepository.existsByCode(OrganizationCode.create(codeGenerator))).isTrue();
        }

        @DisplayName("존재하지 않으면 false를 반환한다.")
        @Test
        void notExist() {
            CodeGenerator codeGenerator = new OrganizationCodeGenerator();
            organizationRepository.save(
                    Organization.create(ORGANIZATION_WOOWACOURSE, codeGenerator, 100, organizationValidator));
            assertThat(organizationRepository.existsByCode(OrganizationCode.create(codeGenerator))).isFalse();
        }
    }

    @DisplayName("동일한 이름이 있는지 확인할 때 ")
    @Nested
    class ExistsByNameTest {

        @DisplayName("존재하면 true를 반환한다.")
        @Test
        void exist() {
            organizationRepository.save(createDefaultOrganization(organizationValidator));
            assertThat(organizationRepository.existsByName(new OrganizationName(ORGANIZATION_WOOWACOURSE))).isTrue();
        }

        @DisplayName("존재하지 않으면 false를 반환한다.")
        @Test
        void notExist() {
            organizationRepository.save(createDefaultOrganization(organizationValidator));
            assertThat(organizationRepository.existsByName(
                    new OrganizationName(ORGANIZATION_WOOWACOURSE + "a"))).isFalse();
        }
    }

    @DisplayName("code로 조직을 찾는다.")
    @Test
    void findByCodeValue() {
        Organization woowacourse = organizationRepository.save(createDefaultOrganization(organizationValidator));
        Organization organization = organizationRepository.findByCodeValue(ORGANIZATION_WOOWACOURSE_CODE).orElseThrow();

        assertThat(organization.getId()).isEqualTo(woowacourse.getId());
    }

    @DisplayName("id로 조직을 찾는다.")
    @Test
    void findWithMemberById() {
        Organization woowacourse = organizationRepository.save(createDefaultOrganization(organizationValidator));
        Organization organization = organizationRepository.findWithMemberById(woowacourse.getId()).orElseThrow();

        assertThat(organization.getId()).isEqualTo(woowacourse.getId());
    }

    @DisplayName("member로 가입 순서로 정렬된 조직-멤버를 찾는다.")
    @Test
    void findOrganizationMembersByMemberOrderByOrderNumber() {
        Member lala = memberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, SKRR_IMAGE_URL));

        Organization organization1 = organizationRepository.save(createDefaultOrganization(organizationValidator));
        Organization organization2 = organizationRepository.save(createThankooOrganization(organizationValidator));

        organization2.join(lala, new OrganizationMembers(List.of()));
        OrganizationMembers joinedOrganizationMembers = new OrganizationMembers(
                organizationRepository.findOrganizationMembersByMember(lala));
        organization1.join(lala, joinedOrganizationMembers);

        List<OrganizationMember> organizationMembers = organizationRepository.findOrganizationMembersByMemberOrderByOrderNumber(
                lala);

        assertThat(organizationMembers).extracting("organization")
                .containsExactly(organization2, organization1);
    }

    @DisplayName("member로 조직-멤버를 찾는다.")
    @Test
    void findOrganizationMembersByMember() {
        Member lala = memberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, SKRR_IMAGE_URL));

        Organization organization1 = organizationRepository.save(createDefaultOrganization(organizationValidator));
        Organization organization2 = organizationRepository.save(createThankooOrganization(organizationValidator));

        organization1.join(lala, new OrganizationMembers(List.of()));
        OrganizationMembers joinedOrganizationMembers = new OrganizationMembers(
                organizationRepository.findOrganizationMembersByMember(lala));
        organization2.join(lala, joinedOrganizationMembers);

        List<OrganizationMember> organizationMembers = organizationRepository.findOrganizationMembersByMember(lala);

        assertThat(organizationMembers).extracting("organization")
                .containsOnly(organization1, organization2);
    }
}

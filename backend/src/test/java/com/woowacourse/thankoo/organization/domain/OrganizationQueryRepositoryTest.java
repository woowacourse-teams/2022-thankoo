package com.woowacourse.thankoo.organization.domain;

import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_IMAGE_URL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_IMAGE_URL;
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
import static com.woowacourse.thankoo.common.fixtures.OrganizationFixture.createDefaultOrganization;
import static com.woowacourse.thankoo.common.fixtures.OrganizationFixture.createThankooOrganization;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

import com.woowacourse.thankoo.common.annotations.RepositoryTest;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@DisplayName("OrganizationQueryRepository 는 ")
@RepositoryTest
class OrganizationQueryRepositoryTest {

    private OrganizationQueryRepository organizationQueryRepository;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private MemberRepository memberRepository;

    private OrganizationValidator organizationValidator;

    @BeforeEach
    void setUp() {
        organizationQueryRepository = new OrganizationQueryRepository(new NamedParameterJdbcTemplate(dataSource));
        organizationValidator = Mockito.mock(OrganizationValidator.class);
        doNothing().when(organizationValidator).validate(any(Organization.class));
    }

    @DisplayName("회원의 조직을 순서대로 조회한다.")
    @Test
    void findMemberOrganizationsByMemberId() {
        com.woowacourse.thankoo.member.domain.Member member = memberRepository.save(
                new com.woowacourse.thankoo.member.domain.Member(HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID,
                        HUNI_IMAGE_URL));
        Organization woowacourse = organizationRepository.save(createDefaultOrganization(organizationValidator));
        Organization thankoo = organizationRepository.save(createThankooOrganization(organizationValidator));

        OrganizationMembers organizationMembers1 = new OrganizationMembers(
                organizationRepository.findOrganizationMembersByMemberOrderByOrderNumber(member));
        woowacourse.join(member, organizationMembers1);

        OrganizationMembers organizationMembers2 = new OrganizationMembers(
                organizationRepository.findOrganizationMembersByMemberOrderByOrderNumber(member));
        thankoo.join(member, organizationMembers2);

        organizationRepository.flush();

        List<MemberOrganization> memberOrganizations =
                organizationQueryRepository.findMemberOrganizationsByMemberId(member.getId());

        assertAll(
                () -> assertThat(memberOrganizations).hasSize(2),
                () -> assertThat(memberOrganizations.get(0).getId()).isEqualTo(woowacourse.getId()),
                () -> assertThat(memberOrganizations.get(0).getOrderNumber()).isEqualTo(1),
                () -> assertThat(memberOrganizations.get(1).getId()).isEqualTo(thankoo.getId()),
                () -> assertThat(memberOrganizations.get(1).getOrderNumber()).isEqualTo(2)
        );
    }

    @DisplayName("조직의 회원 중 내가 아닌 회원을 모두 조회한다.")
    @Test
    void findOrganizationByOrganizationIdNotMemberId() {
        com.woowacourse.thankoo.member.domain.Member lala = memberRepository.save(
                new com.woowacourse.thankoo.member.domain.Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID,
                        LALA_IMAGE_URL));
        com.woowacourse.thankoo.member.domain.Member skrr = memberRepository.save(
                new com.woowacourse.thankoo.member.domain.Member(SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID,
                        SKRR_IMAGE_URL));
        com.woowacourse.thankoo.member.domain.Member hoho = memberRepository.save(
                new com.woowacourse.thankoo.member.domain.Member(HOHO_NAME, HOHO_EMAIL, HOHO_SOCIAL_ID,
                        HOHO_IMAGE_URL));
        com.woowacourse.thankoo.member.domain.Member huni = memberRepository.save(
                new com.woowacourse.thankoo.member.domain.Member(HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID,
                        HUNI_IMAGE_URL));

        Organization organization1 = organizationRepository.save(createDefaultOrganization(organizationValidator));
        Organization organization2 = organizationRepository.save(createThankooOrganization(organizationValidator));

        organization1.join(lala, new OrganizationMembers(List.of()));
        organization1.join(skrr, new OrganizationMembers(List.of()));
        organization1.join(hoho, new OrganizationMembers(List.of()));
        organization2.join(huni, new OrganizationMembers(List.of()));

        organizationRepository.flush();

        List<MemberModel> members = organizationQueryRepository.findOrganizationMembersExcludeMe(organization1.getId(),
                hoho.getId());

        assertAll(
                () -> assertThat(members).hasSize(2),
                () -> assertThat(members.get(0)).extracting("id").isEqualTo(lala.getId()),
                () -> assertThat(members.get(1)).extracting("id").isEqualTo(skrr.getId())
        );
    }
}

package com.woowacourse.thankoo.member.domain;

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
import com.woowacourse.thankoo.organization.domain.MemberModel;
import com.woowacourse.thankoo.organization.domain.Organization;
import com.woowacourse.thankoo.organization.domain.OrganizationMembers;
import com.woowacourse.thankoo.organization.domain.OrganizationRepository;
import com.woowacourse.thankoo.organization.domain.OrganizationValidator;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@DisplayName("MemberQueryRepository 는 ")
@RepositoryTest
class MemberQueryRepositoryTest {

    private MemberQueryRepository memberQueryRepository;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    private OrganizationValidator organizationValidator;

    @BeforeEach
    void setUp() {
        memberQueryRepository = new MemberQueryRepository(jdbcTemplate);
        organizationValidator = Mockito.mock(OrganizationValidator.class);
        doNothing().when(organizationValidator).validate(any(Organization.class));
    }

    @DisplayName("회원이 존재하는지 확인할 때")
    @Nested
    class ExistedMember {

        @DisplayName("존재하면 true를 반환한다.")
        @Test
        void existedById() {
            Member member = memberRepository.save(new Member(HOHO_NAME, HOHO_EMAIL, HOHO_SOCIAL_ID, HUNI_IMAGE_URL));

            assertThat(memberQueryRepository.existsById(member.getId())).isTrue();
        }

        @DisplayName("존재하지 않으면 false를 반환한다.")
        @Test
        void notExistedById() {
            assertThat(memberQueryRepository.existsById(0L)).isFalse();
        }
    }

    @DisplayName("조직의 회원 중 내가 아닌 회원을 모두 조회한다.")
    @Test
    void findOrganizationByOrganizationIdNotMemberId() {
        Member lala = memberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, LALA_IMAGE_URL));
        Member skrr = memberRepository.save(new Member(SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, SKRR_IMAGE_URL));
        Member hoho = memberRepository.save(new Member(HOHO_NAME, HOHO_EMAIL, HOHO_SOCIAL_ID, HOHO_IMAGE_URL));
        Member huni = memberRepository.save(new Member(HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, HUNI_IMAGE_URL));

        Organization organization1 = organizationRepository.save(createDefaultOrganization(organizationValidator));
        Organization organization2 = organizationRepository.save(createThankooOrganization(organizationValidator));

        organization1.join(lala, new OrganizationMembers(List.of()));
        organization1.join(skrr, new OrganizationMembers(List.of()));
        organization1.join(hoho, new OrganizationMembers(List.of()));
        organization2.join(huni, new OrganizationMembers(List.of()));

        organizationRepository.flush();

        List<MemberModel> members = memberQueryRepository.findOrganizationMembersExcludeMe(organization1.getId(),
                hoho.getId());

        assertAll(
                () -> assertThat(members).hasSize(2),
                () -> assertThat(members.get(0)).extracting("id").isEqualTo(lala.getId()),
                () -> assertThat(members.get(1)).extracting("id").isEqualTo(skrr.getId())
        );
    }
}

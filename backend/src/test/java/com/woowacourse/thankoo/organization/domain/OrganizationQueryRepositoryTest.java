package com.woowacourse.thankoo.organization.domain;

import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_IMAGE_URL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.OrganizationFixture.ORGANIZATION_THANKOO;
import static com.woowacourse.thankoo.common.fixtures.OrganizationFixture.ORGANIZATION_WOOWACOURSE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

import com.woowacourse.thankoo.common.annotations.RepositoryTest;
import com.woowacourse.thankoo.member.domain.Member;
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
        Member member = memberRepository.save(new Member(HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, HUNI_IMAGE_URL));

        Organization organization1 = organizationRepository.save(
                Organization.create(ORGANIZATION_WOOWACOURSE, length -> "ABCDEFG1", 100, organizationValidator));

        Organization organization2 = organizationRepository.save(
                Organization.create(ORGANIZATION_THANKOO, length -> "ABCDEFG2", 100, organizationValidator));

        organization1.join(member, 0);
        organization2.join(member, 1);

        organizationRepository.flush();

        List<MemberOrganization> memberOrganizations = organizationQueryRepository.findMemberOrganizationsByMemberId(
                member.getId());

        assertAll(
                () -> assertThat(memberOrganizations).hasSize(2),
                () -> assertThat(memberOrganizations.get(0).getId()).isEqualTo(organization1.getId()),
                () -> assertThat(memberOrganizations.get(0).getOrderNumber()).isEqualTo(1),
                () -> assertThat(memberOrganizations.get(1).getId()).isEqualTo(organization2.getId()),
                () -> assertThat(memberOrganizations.get(1).getOrderNumber()).isEqualTo(2)
        );
    }
}

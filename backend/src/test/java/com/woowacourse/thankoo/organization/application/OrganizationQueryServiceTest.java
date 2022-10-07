package com.woowacourse.thankoo.organization.application;

import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_IMAGE_URL;
import static com.woowacourse.thankoo.common.fixtures.OrganizationFixture.ORGANIZATION_WOOWACOURSE;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.thankoo.common.annotations.ApplicationTest;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import com.woowacourse.thankoo.organization.application.dto.OrganizationJoinRequest;
import com.woowacourse.thankoo.organization.domain.Organization;
import com.woowacourse.thankoo.organization.domain.OrganizationRepository;
import com.woowacourse.thankoo.organization.domain.OrganizationValidator;
import com.woowacourse.thankoo.organization.infrastructure.OrganizationCodeGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("OrganizationQueryService 는 ")
@ApplicationTest
class OrganizationQueryServiceTest {

    @Autowired
    private OrganizationQueryService organizationQueryService;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private OrganizationValidator organizationValidator;

    @DisplayName("회원의 조직을 조회할 때 ")
    @Nested
    class GetOrganizationTest {

        @DisplayName("없으면 빈 리스트를 반환한다.")
        @Test
        void empty() {
            Member lala = memberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, SKRR_IMAGE_URL));

            organizationRepository.save(
                    Organization.create(ORGANIZATION_WOOWACOURSE,
                            new OrganizationCodeGenerator(),
                            100,
                            organizationValidator));

            assertThat(organizationQueryService.getMemberOrganizations(lala.getId())).isEmpty();
        }

        @DisplayName("있으면 반환한다.")
        @Test
        void success() {
            Member lala = memberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, SKRR_IMAGE_URL));

            Organization organization = organizationRepository.save(
                    Organization.create(ORGANIZATION_WOOWACOURSE,
                            new OrganizationCodeGenerator(),
                            100,
                            organizationValidator));

            organizationService.join(lala.getId(), new OrganizationJoinRequest(organization.getCode().getValue()));

            assertThat(organizationQueryService.getMemberOrganizations(lala.getId())).hasSize(1);
        }
    }
}

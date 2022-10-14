package com.woowacourse.thankoo.organization.application;

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

import com.woowacourse.thankoo.common.annotations.ApplicationTest;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import com.woowacourse.thankoo.organization.application.dto.OrganizationJoinRequest;
import com.woowacourse.thankoo.organization.domain.Organization;
import com.woowacourse.thankoo.organization.domain.OrganizationMember;
import com.woowacourse.thankoo.organization.domain.OrganizationRepository;
import com.woowacourse.thankoo.organization.domain.OrganizationValidator;
import com.woowacourse.thankoo.organization.presentation.dto.OrganizationMemberResponse;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("OrganizationService 는 ")
@ApplicationTest
class OrganizationServiceTest {

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private OrganizationValidator organizationValidator;

    @DisplayName("join 할 때 ")
    @Nested
    class JoinTest {

        @DisplayName("join에 성공한다.")
        @Test
        void join() {
            Member lala = memberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, SKRR_IMAGE_URL));
            Organization organization = organizationRepository.save(createDefaultOrganization(organizationValidator));

            organizationService.join(lala.getId(), new OrganizationJoinRequest(organization.getCode().getValue()));
            List<OrganizationMember> organizationMembers = organizationRepository.findOrganizationMembersByMemberOrderByOrderNumber(
                    lala);

            assertAll(
                    () -> assertThat(organizationMembers).hasSize(1),
                    () -> assertThat(organizationMembers.get(0).getOrderNumber()).isEqualTo(1),
                    () -> assertThat(organizationMembers.get(0).isLastAccessed()).isTrue()
            );
        }

        @DisplayName("새 조직에 참여하면 새 조직이 lastAccessed가 된다.")
        @Test
        void joinLastAccessed() {
            Member lala = memberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, SKRR_IMAGE_URL));

            Organization woowacourse = organizationRepository.save(createDefaultOrganization(organizationValidator));
            Organization thankoo = organizationRepository.save(createThankooOrganization(organizationValidator));

            organizationService.join(lala.getId(), new OrganizationJoinRequest(woowacourse.getCode().getValue()));
            organizationService.join(lala.getId(), new OrganizationJoinRequest(thankoo.getCode().getValue()));

            List<OrganizationMember> organizationMembers = organizationRepository.findOrganizationMembersByMemberOrderByOrderNumber(
                    lala);

            assertAll(
                    () -> assertThat(organizationMembers).hasSize(2),
                    () -> assertThat(organizationMembers.get(0).isLastAccessed()).isFalse(),
                    () -> assertThat(organizationMembers.get(1).isLastAccessed()).isTrue()
            );
        }
    }

    @DisplayName("조직에 접근하면 해당 조직을 최근 접근 조직으로 변경한다.")
    @Test
    void access() {
        Member lala = memberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, SKRR_IMAGE_URL));

        Organization woowacourse = organizationRepository.save(createDefaultOrganization(organizationValidator));
        Organization thankoo = organizationRepository.save(createThankooOrganization(organizationValidator));

        organizationService.join(lala.getId(), new OrganizationJoinRequest(woowacourse.getCode().getValue()));
        organizationService.join(lala.getId(), new OrganizationJoinRequest(thankoo.getCode().getValue()));
        organizationService.access(lala.getId(), woowacourse.getId());

        List<OrganizationMember> organizationMembers = organizationRepository.findOrganizationMembersByMemberOrderByOrderNumber(
                lala);

        assertAll(
                () -> assertThat(organizationMembers).hasSize(2),
                () -> assertThat(organizationMembers.get(0).isLastAccessed()).isTrue(),
                () -> assertThat(organizationMembers.get(1).isLastAccessed()).isFalse()
        );
    }

    @DisplayName("나를 제외한 조직의 회원을 조회한다.")
    @Test
    void getOrganizationMembersExcludeMe() {
        Member lala = memberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, LALA_IMAGE_URL));
        Member skrr = memberRepository.save(new Member(SKRR_NAME, SKRR_EMAIL, SKRR_SOCIAL_ID, SKRR_IMAGE_URL));
        Member hoho = memberRepository.save(new Member(HOHO_NAME, HOHO_EMAIL, HOHO_SOCIAL_ID, HOHO_IMAGE_URL));
        Member huni = memberRepository.save(new Member(HUNI_NAME, HUNI_EMAIL, HUNI_SOCIAL_ID, HUNI_IMAGE_URL));

        Organization woowacourse = organizationRepository.save(createDefaultOrganization(organizationValidator));
        Organization thankoo = organizationRepository.save(createThankooOrganization(organizationValidator));

        organizationService.join(lala.getId(), new OrganizationJoinRequest(woowacourse.getCode().getValue()));
        organizationService.join(skrr.getId(), new OrganizationJoinRequest(woowacourse.getCode().getValue()));
        organizationService.join(hoho.getId(), new OrganizationJoinRequest(woowacourse.getCode().getValue()));
        organizationService.join(huni.getId(), new OrganizationJoinRequest(thankoo.getCode().getValue()));

        List<OrganizationMemberResponse> members = organizationService.getOrganizationMembersExcludeMe(
                hoho.getId(), woowacourse.getId());

        assertAll(
                () -> assertThat(members).hasSize(2),
                () -> assertThat(members.get(0).getId()).isEqualTo(lala.getId()),
                () -> assertThat(members.get(1).getId()).isEqualTo(skrr.getId())
        );
    }
}

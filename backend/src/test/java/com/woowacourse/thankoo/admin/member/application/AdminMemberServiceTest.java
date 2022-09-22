package com.woowacourse.thankoo.admin.member.application;

import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_IMAGE_URL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.SKRR_IMAGE_URL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.thankoo.admin.member.application.dto.AdminMemberNameRequest;
import com.woowacourse.thankoo.admin.member.application.dto.AdminMemberSearchRequest;
import com.woowacourse.thankoo.admin.member.domain.AdminMemberRepository;
import com.woowacourse.thankoo.admin.member.exception.AdminNotFoundMemberException;
import com.woowacourse.thankoo.admin.member.presentation.dto.AdminMemberResponse;
import com.woowacourse.thankoo.common.annotations.ApplicationTest;
import com.woowacourse.thankoo.member.domain.Member;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("AdminMemberService 는 ")
@ApplicationTest
class AdminMemberServiceTest {

    @Autowired
    private AdminMemberService adminMemberService;

    @Autowired
    private AdminMemberRepository adminMemberRepository;

    @DisplayName("모든 회원 정보를 조회한다.")
    @Test
    void getMembers() {
        adminMemberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, SKRR_IMAGE_URL));
        adminMemberRepository.save(new Member(HOHO_NAME, HOHO_EMAIL, HOHO_SOCIAL_ID, HUNI_IMAGE_URL));

        LocalDate startDate = LocalDate.now().minusDays(1L);
        LocalDate endDate = LocalDate.now();
        AdminMemberSearchRequest adminMemberSearchRequest = new AdminMemberSearchRequest(startDate, endDate);

        List<AdminMemberResponse> members = adminMemberService.getMembers(adminMemberSearchRequest);

        assertThat(members).hasSize(2);
    }

    @DisplayName("회원의 이름을 변경할 때 ")
    @Nested
    class UpdateMemberName {

        @DisplayName("정상적으로 회원의 이름을 변경한다.")
        @Test
        void updateMemberName() {
            Member member = adminMemberRepository.save(
                    new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, SKRR_IMAGE_URL));
            adminMemberService.updateMemberName(member.getId(), new AdminMemberNameRequest(HOHO_NAME));

            Member updatedMember = adminMemberRepository.findById(member.getId()).get();

            assertThat(updatedMember.getName().getValue()).isEqualTo(HOHO_NAME);
        }

        @DisplayName("존재하지 않는 회원일 경우 예외가 발생한다.")
        @Test
        void updateNameWithInvalidMember() {
            Member member = adminMemberRepository.save(
                    new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, SKRR_IMAGE_URL));

            assertThatThrownBy(() -> adminMemberService.updateMemberName(member.getId() + 1,
                    new AdminMemberNameRequest(HOHO_NAME)))
                    .isInstanceOf(AdminNotFoundMemberException.class)
                    .hasMessage("존재하지 않는 회원입니다.");
        }
    }
}

package com.woowacourse.thankoo.member.application;

import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_IMAGE_URL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_IMAGE_URL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_IMAGE_URL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_SOCIAL_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.thankoo.common.annotations.ApplicationTest;
import com.woowacourse.thankoo.member.application.dto.MemberNameRequest;
import com.woowacourse.thankoo.member.application.dto.MemberProfileImageRequest;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import com.woowacourse.thankoo.member.exception.InvalidMemberException;
import com.woowacourse.thankoo.member.presentation.dto.MemberResponse;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("MemberService 는 ")
@ApplicationTest
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("본인을 제외한 모든 회원을 조회한다.")
    @Test
    void getMembersExcludeMe() {
        Member member = memberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, LALA_IMAGE_URL));
        memberRepository.save(new Member(HOHO_NAME, HOHO_EMAIL, HOHO_SOCIAL_ID, HOHO_IMAGE_URL));
        memberRepository.save(new Member(HUNI_NAME, HUNI_EMAIL, HUNI_EMAIL, HUNI_IMAGE_URL));

        List<MemberResponse> memberResponses = memberService.getMembersExcludeMe(member.getId());

        assertAll(
                () -> assertThat(memberResponses).hasSize(2),
                () -> assertThat(memberResponses).extracting("name").containsExactly(HOHO_NAME, HUNI_NAME)
        );
    }

    @DisplayName("내 정보를 조회한다.")
    @Test
    void getMember() {
        Member member = memberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, LALA_IMAGE_URL));

        MemberResponse memberResponse = memberService.getMember(member.getId());

        assertAll(
                () -> assertThat(memberResponse.getName()).isEqualTo(LALA_NAME),
                () -> assertThat(memberResponse.getEmail()).isEqualTo(LALA_EMAIL),
                () -> assertThat(memberResponse.getImageUrl()).isEqualTo(LALA_IMAGE_URL)
        );
    }

    @DisplayName("회원 이름을 수정할 때 ")
    @Nested
    class UpdateNameTest {

        @DisplayName("회원이 존재하면 정상적으로 이름을 수정한다.")
        @Test
        void updateMemberName() {
            Member member = memberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, LALA_IMAGE_URL));
            memberService.updateMemberName(member.getId(), new MemberNameRequest(HUNI_NAME));

            Member foundMember = memberRepository.findById(member.getId()).orElseThrow();

            assertAll(
                    () -> assertThat(foundMember).isNotNull(),
                    () -> assertThat(foundMember.getName().getValue()).isEqualTo(HUNI_NAME)
            );
        }

        @DisplayName("회원이 존재하지 않으면 예외가 발생한다.")
        @Test
        void updateNameException() {
            assertThatThrownBy(() -> memberService.updateMemberName(0L, new MemberNameRequest(HUNI_NAME)))
                    .isInstanceOf(InvalidMemberException.class)
                    .hasMessage("존재하지 않는 회원입니다.");
        }
    }

    @DisplayName("회원 프로필 이미지를 수정할 때 ")
    @Nested
    class UpdateProfileImageTest {

        @DisplayName("회원이 존재하면 정상적으로 프로필 이미지를 수정한다.")
        @Test
        void updateMemberName() {
            Member member = memberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, LALA_IMAGE_URL));
            memberService.updateMemberProfileImage(member.getId(), new MemberProfileImageRequest(HUNI_IMAGE_URL));

            Member foundMember = memberRepository.findById(member.getId()).orElseThrow();

            assertAll(
                    () -> assertThat(foundMember).isNotNull(),
                    () -> assertThat(foundMember.getImageUrl()).isEqualTo(HUNI_IMAGE_URL)
            );
        }

        @DisplayName("회원이 존재하지 않으면 예외가 발생한다.")
        @Test
        void updateNameException() {
            assertThatThrownBy(() -> memberService.updateMemberProfileImage(0L, new MemberProfileImageRequest(
                    HUNI_IMAGE_URL)))
                    .isInstanceOf(InvalidMemberException.class)
                    .hasMessage("존재하지 않는 회원입니다.");
        }
    }

    @DisplayName("모든 회원 프로필 이미지들을 조회한다.")
    @Test
    void getProfileImages() {
        assertThat(memberService.getProfileImages()).hasSizeGreaterThanOrEqualTo(0);
    }
}

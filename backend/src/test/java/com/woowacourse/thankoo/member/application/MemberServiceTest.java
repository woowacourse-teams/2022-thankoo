package com.woowacourse.thankoo.member.application;

import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HOHO_SOCIAL_ID;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.HUNI_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.IMAGE_URL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_EMAIL;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_NAME;
import static com.woowacourse.thankoo.common.fixtures.MemberFixture.LALA_SOCIAL_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.thankoo.authentication.infrastructure.dto.GoogleProfileResponse;
import com.woowacourse.thankoo.member.application.dto.MemberNameRequest;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
import com.woowacourse.thankoo.member.exception.InvalidMemberException;
import com.woowacourse.thankoo.member.presentation.dto.MemberResponse;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@DisplayName("MemberService 는 ")
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("SignIn 요청이 왔을 때 ")
    @Nested
    class SignInTest {

        @DisplayName("회원이 존재하지 않으면 생성한다.")
        @Test
        void signInCreateMember() {
            Long id = memberService.createOrGet(new GoogleProfileResponse("1056", "example@email.com",
                    "image.com"));

            assertAll(
                    () -> assertThat(id).isNotNull(),
                    () -> assertThat(memberRepository.findAll()).hasSize(1)
            );
        }

        @DisplayName("회원이 존재하면 조회한다.")
        @Test
        void signInGetMember() {
            memberRepository.save(new Member(HOHO_NAME, HOHO_EMAIL, HOHO_SOCIAL_ID, IMAGE_URL));
            Long id = memberService.createOrGet(new GoogleProfileResponse(HOHO_SOCIAL_ID, HOHO_EMAIL, IMAGE_URL));

            assertAll(
                    () -> assertThat(id).isNotNull(),
                    () -> assertThat(memberRepository.findAll()).hasSize(1)
            );
        }
    }

    @DisplayName("본인을 제외한 모든 회원을 조회한다.")
    @Test
    void getMembersExcludeMe() {
        Member member = memberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, IMAGE_URL));
        memberRepository.save(new Member(HOHO_NAME, HOHO_EMAIL, HOHO_SOCIAL_ID, IMAGE_URL));
        memberRepository.save(new Member(HUNI_NAME, HUNI_EMAIL, HUNI_EMAIL, IMAGE_URL));

        List<MemberResponse> memberResponses = memberService.getMembersExcludeMe(member.getId());

        assertAll(
                () -> assertThat(memberResponses).hasSize(2),
                () -> assertThat(memberResponses).extracting("name").containsExactly(HOHO_NAME, HUNI_NAME)
        );
    }

    @DisplayName("내 정보를 조회한다.")
    @Test
    void getMember() {
        Member member = memberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, IMAGE_URL));

        MemberResponse memberResponse = memberService.getMember(member.getId());

        assertThat(memberResponse).usingRecursiveComparison()
                .isEqualTo(MemberResponse.of(member));
    }

    @Nested
    @DisplayName("회원 이름을 수정할 때 ")
    class UpdateNameTest {

        @DisplayName("정상적으로 이름을 수정한다.")
        @Test
        void updateMemberName() {
            Member member = memberRepository.save(new Member(LALA_NAME, LALA_EMAIL, LALA_SOCIAL_ID, IMAGE_URL));
            memberService.updateMemberName(member.getId(), new MemberNameRequest(HUNI_NAME));

            Member foundMember = memberRepository.findById(member.getId()).get();

            assertAll(
                    () -> assertThat(foundMember).isNotNull(),
                    () -> assertThat(foundMember.getName()).isEqualTo(HUNI_NAME)
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

    @AfterEach
    void tearDown() {
        memberRepository.deleteAllInBatch();
    }
}

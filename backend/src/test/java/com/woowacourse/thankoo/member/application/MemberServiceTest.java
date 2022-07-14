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
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.thankoo.authentication.infrastructure.dto.GoogleProfileResponse;
import com.woowacourse.thankoo.member.domain.Member;
import com.woowacourse.thankoo.member.domain.MemberRepository;
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

        @DisplayName("회원가 존재하지 않으면 생성한다.")
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

    @AfterEach
    void tearDown() {
        memberRepository.deleteAllInBatch();
    }
}

package com.woowacourse.thankoo.member.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

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
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("SignIn 요청이 왔을 때 ")
    @Nested
    class SignInTest {

        @DisplayName("멤버가 존재하지 않으면 생성한다.")
        @Test
        void signInCreateMember() {
            Long id = memberService.createOrGet("hoho");

            assertAll(
                    () -> assertThat(id).isNotNull(),
                    () -> assertThat(memberRepository.findAll()).hasSize(1)
            );
        }

        @DisplayName("멤버가 존재하면 조회한다.")
        @Test
        void signInGetMember() {
            memberRepository.save(new Member("hoho"));
            Long id = memberService.createOrGet("hoho");

            assertAll(
                    () -> assertThat(id).isNotNull(),
                    () -> assertThat(memberRepository.findAll()).hasSize(1)
            );
        }
    }

    @DisplayName("본인을 제외한 모든 회원을 조회한다.")
    @Test
    void getMembersExcludeMe() {
        Member member = memberRepository.save(new Member("hoho"));
        memberRepository.save(new Member("huni"));
        memberRepository.save(new Member("skrrr"));

        List<MemberResponse> memberResponses = memberService.getMembersExcludeMe(member.getId());

        assertAll(
                () -> assertThat(memberResponses).hasSize(2),
                () -> assertThat(memberResponses).extracting("name").containsExactly("huni", "skrrr")
        );
    }

    @AfterEach
    void tearDown() {
        memberRepository.deleteAllInBatch();
    }
}
